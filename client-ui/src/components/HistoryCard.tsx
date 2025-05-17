import Image from "next/image";
import { X} from "lucide-react";
import {ListEpisodeHistory} from "@/types/list-episode-history";
import Link from "next/link";

export default function HistoryCard(
    {
        episode,
        onDelete,
    }:
    {
        episode: ListEpisodeHistory,
        onDelete: (episodeId: string) => void
    }
){
    return (
        <Link  href={`/film/${slug(episode)}/episode-${episode.episodeNumber}}`}
            className={"flex flex-row w-full cursor-pointer hover:scale-102 duration-200 space-x-4 rounded-md"}
            style={{background: "#FFFFFF1A"}}>
            <div className=" w-45 h-30 relative">
                <Image
                    className="rounded-md object-cover"
                    fill={true}
                    src={episode.thumbnail?.url || "https://placehold.co/200x300/png?text=ani4h"}
                    alt={episode.title}
                />
                <div className="absolute bottom-0 left-0 w-full bg-white h-1 rounded-b-md overflow-hidden">
                    <div className="bg-orange-600 h-full"
                         style={{ width: `${episode.watchedDuration/episode.duration*100}%`}}>
                    </div>
                </div>
            </div>
            <div className={"flex flex-col flex-1 mt-2 pr-2 space-y-1"}>
                <div className={"flex flex-row justify-between"}>
                    <p className="font-bold line-clamp-1">{episode.title}</p>
                    <X
                        className="text-white hover:scale-110 hover:text-orange-500 flex-shrink-0"
                        strokeWidth={3}
                        size={22}
                        onClick={(e) => {
                            e.stopPropagation();
                            e.preventDefault();
                            onDelete(episode.id);
                        }}
                    />
                </div>
                <p className="font-normal ">Views: {episode.viewCount}</p>
                <p className="font-normal opacity-50 line-clamp-2">{episode.synopsis}</p>
            </div>
        </Link>
    )
}
function slug(episode : ListEpisodeHistory): string {
    return episode.filmTitle.toLowerCase().split(" ").join("-")+'-'+episode.filmId
}