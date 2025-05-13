'use client'
import HistoryCard from "@/components/HistoryCard";
import {useEffect, useRef, useState} from "react";
import {ListEpisodeHistory} from "@/types/list-episode-history";
import {HistoryService} from "@/app/profile/[slug]/history/service";
import {useParams} from "next/navigation";

export default function HistoryPage() {
    const params = useParams();
    const userId = params.slug as string;

    const [data, setData] = useState<ListEpisodeHistory[]>([])
    const [paging, setPaging] = useState<Paging>({
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 10,
    })
    const loader = useRef(null);
    const [hasMore, setHasMore] = useState(true);
    const [isLoading, setIsLoading] = useState(false);

    const fetchFirstData = async () => {
        setIsLoading(true);
        try {
            const res = await HistoryService.getWatchedEpisodes(userId, paging);
            setData(res.data);
            if (res.data.length === 0) {
                setHasMore(false);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    }

    const fetchMore = async () => {
        if(isLoading || !hasMore) return;
        const newPaging = {
            ...paging,
            page: paging.page + 1,
        }
        setPaging(newPaging);

        setIsLoading(true);
        try{
            const res = await HistoryService.getWatchedEpisodes(userId, newPaging);
            setData(prev => [...prev, ...res.data]);
            if(res.data.length === 0){
                setHasMore(false);
            }
        }
        catch (error) {
            console.error("Error fetching data:", error);
        }
        finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        fetchFirstData();
    }, []);

    useEffect(() => {
        if(!loader.current || !hasMore || isLoading || data.length === 0) return;

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

    const handleDelete = async (episodeId: string) => {
        try {
            await HistoryService.deleteHistory(userId, episodeId)
                .then(res => {
                    console.log("Delete success: ", res);
                    setData(prev => prev.filter(item => item.id !== episodeId));
                })
                .catch(err => {
                    console.error("Delete error: ", err);
                });
        }
        catch (error) {
            console.error("Error deleting episode:", error);
        }
    }

    return (
        <div>
            <div className={"flex flex-col w-full space-y-8 ml-8"}>
                <p className={"font-bold text-2xl text-orange-500"}>History</p>
                <div className="overflow-y-auto h-[500px] pr-4 mr-10">
                    <div className={"flex flex-col space-y-4"}>
                        {data.map((episode, i) =>
                            <HistoryCard episode={episode} key={i} onDelete={handleDelete} />
                        )}
                    </div>
                    {hasMore && <div ref={loader} style={{ height: "40px" }} />}
                    {!hasMore && <p>Hết dữ liệu.</p>}
                </div>
            </div>
        </div>
    )
}