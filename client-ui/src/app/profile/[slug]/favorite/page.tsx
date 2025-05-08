'use client'
import {SearchList} from "@/types/search/searchList";
import FavoriteCard from "@/components/FavoriteCard";
import {FavoriteService} from "@/app/profile/[slug]/favorite/service";
import {useEffect, useRef, useState} from "react";

export default function FavoritePage() {
    const [paging, setPaging] = useState<Paging>(
        {
            cursor: "",
            nextCursor: "",
            page: 1,
            pageSize: 10,
        }
    )
    const [data, setData] = useState<SearchList[]>([]);
    const loader = useRef(null);
    const [hasMore, setHasMore] = useState(true);
    const userId: string = "3w5rMJ7r2JjRwM"
    const [isLoading, setIsLoading] = useState(false);

    const fetchData = async () => {
        console.log("Loding + Hasmore: ", isLoading, hasMore);
        if (isLoading || !hasMore) return;

        setIsLoading(true);
        try {
            console.log("Fetchingggggggggggggg: ", paging);
            const res = await FavoriteService.getFavorites(userId, paging);
            setData(prev => [...prev, ...res.data]);
            if (res.data.length === 0) {
                setHasMore(false);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        fetchData()
    }, [paging]);

    useEffect(() => {
        if (!loader.current || !hasMore || isLoading || data.length === 0) return;

        console.log("Observeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: ", paging);
        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !isLoading) {
                setPaging(prev => ({
                    ...prev,
                    page: prev.page + 1,
                }));
            }
        }, {
            root: null,
            rootMargin: "100px",
            threshold: 0,
        });

        observer.observe(loader.current);

        return () => {
            if (loader.current) {
                observer.unobserve(loader.current);
            }
        };
    }, [hasMore, isLoading]);

    return (
        <div className={"flex flex-col w-full space-y-8 ml-8"}>
            <p className={"font-bold text-2xl text-orange-500"}>Favorite</p>
            <div className="overflow-y-auto h-[500px] pr-4">
            <div className={"grid grid-cols-2 gap-4"}>
                    {data.map((film, i) =>
                        <FavoriteCard film={film} key={i} />
                    )}
                </div>
                {hasMore && <div ref={loader} style={{ height: "40px" }} />}
                {!hasMore && <p>Hết dữ liệu.</p>}
            </div>
        </div>
    )
}