'use client'
import {SearchList} from "@/types/search/searchList";
import SearchListCard from "@/components/SearchListCard";
import FilmCard from "@/components/FilmCard";
import {useSearchParams} from "next/navigation";
import {useEffect, useRef, useState} from "react";
import {PagingSearch} from "@/types/search/pagingSearch";
import { Suspense } from 'react'
import {SearchService} from "@/app/search/service";
import {Paging} from "@/types/paging";

function Search() {
    const userId = "3w5rMJ7r2JjRwM"
    const searchParams = useSearchParams();
    const title = searchParams.get("q") || "";
    const [data, setData] = useState<SearchList[]>([]);
    const [proposal, setProposal] = useState<SearchList[]>([]);
    const [topHot, setTopHot] = useState<SearchList[]>([]);
    const [paging, setPaging] = useState<PagingSearch>({
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 10,
    });
    const loader = useRef(null);
    const [hasMore, setHasMore] = useState(true);
    const [isLoading, setIsLoading] = useState(false);

    const fetchFirst = async () => {
        if(!title) return;

        const newPaging: PagingSearch = {
            cursor: "",
            nextCursor: "",
            page: 1,
            pageSize: 10,
        }

        setIsLoading(true);
        try{
            const res = await SearchService.search(title, newPaging);
            if(res.data) {
                setData(res.data.data);
                setPaging({
                    ...paging,
                    nextCursor: res.data.paging.nextCursor,
                });

                if(res.data.paging.nextCursor === null) {
                    setHasMore(false);
                }
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    const fetchMore = async () => {
        if(isLoading || !hasMore) return;

        const newPaging: PagingSearch = {
            cursor: paging.nextCursor,
            nextCursor: paging.nextCursor,
            page: paging.page + 1,
            pageSize: paging.pageSize,
        }

        setIsLoading(true);
        try{
            const res = await SearchService.search(title, newPaging);
            if (res.data) {
                setData((prev) => [...prev, ...res.data.data]);
                setPaging({
                    ...paging,
                    nextCursor: res.data.paging.nextCursor,
                });

                if(res.data.paging.nextCursor === null) {
                    setHasMore(false);
                }
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        const fetchProposal = async () => {
            const newPaging: PagingSearch = {
                cursor: "",
                nextCursor: "",
                page: 1,
                pageSize: 10,
            }

            try {
                const res = await SearchService.userFavoriteRecommendation(userId, 1, newPaging);
                if(res.data) {
                    setProposal(res.data.data);
                }
            } catch (error) {
                console.error("Error fetching proposal data:", error);
            }
        }

        const fetchTopHot = async () => {
            const newPaging: Paging = {
                cursor: "",
                nextCursor: "",
                page: 1,
                pageSize: 10,
            }

            try {
                const res = await SearchService.getTopHot(newPaging);
                if(res) {
                    setTopHot(res.data);
                }
            } catch (error) {
                console.error("Error fetching top hot data:", error);
            }
        }

        fetchTopHot();

        fetchProposal();
    }, [])

    useEffect(() => {
        if(!title) return;
        console.log("Title: ", title);
        setData([]);
        setHasMore(true);
        fetchFirst();
    }, [title]);

    useEffect(() => {
        if(!loader.current || !hasMore || isLoading || data.length === 0) return;

        const observer = new IntersectionObserver((entries) => {
            if(entries[0].isIntersecting && !isLoading && hasMore){
                fetchMore();
            }
        }, {
            root: null,
            rootMargin: "100px",
            threshold: 0,
        })

        observer.observe(loader.current);

        return () => {
            if(loader.current){
                observer.unobserve(loader.current);
            }
        };

    }, [hasMore, isLoading]);

    return (
        <Suspense>
            <div className={"w-screen mt-[64px] pl-20 pr-20 pt-8"}>
            <div className={"flex flex-row w-full h-full space-x-4"}>
                <div className={"flex flex-col flex-1 h-full space-y-8"}>
                    <div className={"flex flex-col space-y-4"}>
                        {
                            data.map((film, index) =>
                                <SearchListCard film={film} key={index} />
                            )
                        }
                    </div>
                    {hasMore && <div ref={loader} style={{ height: "40px" }} />}
                    {!hasMore && <p>Hết dữ liệu.</p>}
                </div>

                <div className={"flex flex-col w-86 h-full space-y-4"}>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Proposal for you</p>
                        <div className={"flex flex-row overflow-x-auto h-50 space-x-2"}>
                            {
                                proposal.map((film, index) =>
                                    <FilmCard key={index} film={film} height={32} width={24} fontSize={14}/>
                                )
                            }
                        </div>
                    </div>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Top search</p>
                        {topHot.map((film, index) => {
                            let rankColor = "text-gray-400"; // default
                            if (index === 0) rankColor = "text-orange-500";
                            else if (index === 1) rankColor = "text-orange-400";
                            else if (index === 2) rankColor = "text-orange-300";

                            return (
                                <div key={index} className="flex flex-row items-center space-x-2">
                                    <p className={`text-xl font-bold ${rankColor}`}>{index + 1}</p>
                                    <FilmCard film={film} row={true} height={20} width={16} fontSize={14} />
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </div>
        </Suspense>
    );
}

export default function SearchPage() {
    return (
        <Suspense>
            <Search />
        </Suspense>
    )
}


/*
function buildSearchQuery(title: string, paging: PagingSearch): string {
    const params = new URLSearchParams();
    params.append("title", title);
    if(paging.cursor) {
        params.append("cursor", paging.cursor);
    }
    if(paging.nextCursor){
        params.append("nextCursor", paging.nextCursor);
    }
    params.append("page", paging.page.toString());
    params.append("pageSize", paging.pageSize.toString());
    return params.toString();
}*/
