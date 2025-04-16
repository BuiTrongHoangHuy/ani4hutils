import {SearchList} from "@/types/searchList";
import Image from "next/image";
import Link from "next/link";
import {Trash, Trash2} from "lucide-react";

export default function FavoriteCard(
    {film}: {film: SearchList}
){
    return (
        <Link href={`/film/${slug(film)}`}>
            <div
                className={"flex flex-row cursor-pointer hover:scale-103 duration-200 space-x-2 rounded-md"}
                style={{background: "#FFFFFF1A"}}>
                <div className="w-30 h-38 space-y-3 relative">
                    <Image
                        className="rounded-md object-cover"
                        fill={true}
                        src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                        alt={film.title}
                    />
                </div>
                <div className={"flex flex-1 flex-col space-y-2"}>
                    <p className="line-clamp-1 font-bold">{film.title}</p>
                    <p className="line-clamp-2 opacity-60">{film.synopsis}</p>
                    <p>Genres: {film.genres.join(', ')}</p>
                    <div className={"flex justify-end pr-4"}>
                        <Trash className={"text-white hover:scale-110 hover:text-orange-500"} size={20} strokeWidth={3}/>
                    </div>
                </div>

            </div>
        </Link>
    )
}

function slug(film : SearchList): string {
    return film.title.toLowerCase().split(" ").join("-")+'-'+film.id
}