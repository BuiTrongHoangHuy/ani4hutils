'use client'
import {SearchList} from "@/types/search/searchList";
import SearchListCard from "@/components/SearchListCard";
import FilmCard from "@/components/FilmCard";
import {useSearchParams} from "next/navigation";
import {useEffect, useRef, useState} from "react";
import {PagingSearch} from "@/types/search/pagingSearch";

export default function SearchPage() {
    const searchParams = useSearchParams();
    const title = searchParams.get("q") || "";
    const [data, setData] = useState<SearchList[]>([]);
    const [canFetch, setCanFetch] = useState(false);
    const [paging, setPaging] = useState<PagingSearch>({
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 10,
    });
    const loader = useRef(null);
    const [hasMore, setHasMore] = useState(true);

    const fetchData = async () => {
        try{
            console.log("Paging: ", paging);
            const res = await fetch(`http://localhost:4000/v1/search?${buildSearchQuery(title, paging)}`)
            const result = await res.json()
            if (result.data) {
                setData((prev) => [...prev, ...result.data.data]);
                setPaging({
                    ...paging,
                    cursor: result.data.paging.cursor,
                    nextCursor: result.data.paging.nextCursor,
                });

                if(result.data.paging.nextCursor === null) {
                    setHasMore(false);
                }
                else {
                    setHasMore(true);
                }
            } else {
                console.error("Error fetching data:", result);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    useEffect(() => {
        if(title){
            setPaging({
                cursor: "",
                nextCursor: "",
                page: 1,
                pageSize: 10,
            });
            setData([]);
            setHasMore(true);
            setCanFetch(true);
        }
    }, [title]);

    useEffect(() => {
        if(canFetch){
            console.log("Fetching data: UseEffect CanFetch");
            fetchData();
            setCanFetch(false);
        }
    }, [canFetch]);

    useEffect(() => {
        if(paging.cursor !== "" && paging.cursor === paging.nextCursor && paging.nextCursor !== null){
            console.log("Fetching data: UseEffect Cursor");
            fetchData();
        }
    }, [paging.cursor]);

    useEffect(() => {
        if(!loader.current || !hasMore) return;

        const observer = new IntersectionObserver((entries) => {
            if(entries[0].isIntersecting){
                setPaging((prev) => ({
                    ...prev,
                    cursor: prev.nextCursor,
                }));
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

    }, [loader, hasMore]);

    return (
        <div className={"w-screen mt-[64px] pl-20 pr-20 pt-8"}>
            <div className={"flex flex-row w-full h-full space-x-4"}>
                <div className={"flex flex-col flex-1 h-full space-y-8"}>
                    <div>
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
                        <div className={"flex flex-row overflow-x-auto h-45 space-x-2"}>
                            {
                                data.map((film, index) =>
                                    <FilmCard key={index} film={film} height={32} width={24} fontSize={14}/>
                                )
                            }
                        </div>
                    </div>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Top search</p>
                        {data.map((film, index) => {
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
    );
}

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
}