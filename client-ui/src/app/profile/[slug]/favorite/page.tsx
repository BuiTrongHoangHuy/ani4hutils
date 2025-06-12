'use client'
import {SearchList} from "@/types/search/searchList";
import FavoriteCard from "@/components/FavoriteCard";
import {FavoriteService} from "@/app/profile/[slug]/favorite/service";
import {useEffect, useRef, useState} from "react";
import {useParams} from "next/navigation";
import {Paging} from "@/types/paging";

export default function FavoritePage() {
    const params = useParams();
    const userId = params.slug as string;

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
    const [isLoading, setIsLoading] = useState(false);

    const fetchFirstData = async () => {

        setIsLoading(true);
        try {
            const res = await FavoriteService.getFavorites(userId, paging);
            const favoriteData = res.data || [];
            console.log("Favorites: ", favoriteData);
            setData(favoriteData);
            if (favoriteData=== 0) {
                setHasMore(false);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    const fetchMore = async () => {
        console.log("Loding + Hasmore: ", isLoading, hasMore);
        if (isLoading || !hasMore) return;

        const newPaging = {
            ...paging,
            page: paging.page + 1,
        }

        setPaging(newPaging);

        setIsLoading(true);
        try {
            const res = await FavoriteService.getFavorites(userId, newPaging);
            const favoriteData = res.data || [];
            setData(prev => [...prev, ...favoriteData]);
            if ( favoriteData.length === 0) {
                setHasMore(false);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        fetchFirstData();
    }, []);

    useEffect(() => {
        if (!loader.current || !hasMore || isLoading || data.length === 0) return;

        console.log("Observeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: ", paging);
        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !isLoading) {
                fetchMore();
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

    const handleDelete = async (filmId: string) => {
        try {
            await FavoriteService.removeFavorite(userId, filmId)
                .then(res => {
                    console.log("Delete success: ", res);
                    setData(prev => prev.filter(item => item.id !== filmId));
                })
                .catch(err => {
                    console.error("Delete error: ", err);
                });
        } catch (error) {
            console.error("Error deleting favorite:", error);
        }
    }

    return (
        <div className={"flex flex-col w-full space-y-8 ml-8"}>
            <p className={"font-bold text-2xl text-orange-500"}>Favorite</p>
            <div className="overflow-y-auto h-[500px] pr-4">
                <div className={"grid grid-cols-2 gap-4"}>
                    {data && data.map((film, i) =>
                        <FavoriteCard film={film} key={i} onDelete={handleDelete}/>
                    )}
                </div>
                {hasMore && <div ref={loader} style={{ height: "40px" }} />}
                {!hasMore && <p>Hết dữ liệu.</p>}
            </div>
        </div>
    )
}