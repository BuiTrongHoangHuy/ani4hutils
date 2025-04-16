import {SearchList} from "@/types/searchList";
import Image from "next/image";
import {Trash} from "lucide-react";

export default function HistoryCard(
    {film}: {film: SearchList}
){
    return (
        <div
            className={"flex flex-col w-56 cursor-pointer hover:scale-103 duration-200 space-y-2 rounded-md"}
            >
            <div className="w-56 h-38 relative">
                <Image
                    className="rounded-md object-cover"
                    fill={true}
                    src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                    alt={film.title}
                />
                <div className="absolute bottom-0 left-0 w-full bg-white/80 h-2 rounded-b-md overflow-hidden">
                    <div className="bg-orange-500 h-full" style={{ width: '50%' }}></div>
                </div>
            </div>
            <p className="line-clamp-2">{film.title}</p>
        </div>
    )
}