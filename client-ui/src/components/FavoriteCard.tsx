import {SearchList} from "@/types/search/searchList";
import Image from "next/image";
import Link from "next/link";
import { X} from "lucide-react";

export default function FavoriteCard(
    {
        film,
        onDelete,
    }: {
        film: SearchList,
        onDelete: (filmId: string) => void
    }
){
    return (
        <Link href={`/film/${slug(film)}`}>
            <div
                className={"flex flex-row cursor-pointer hover:scale-103 duration-200 space-x-2 rounded-md"}
                style={{background: "#FFFFFF1A"}}>
                <div className="w-24 h-30 space-y-3 relative">
                    <Image
                        className="rounded-md object-cover"
                        fill={true}
                        src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                        alt={film.title}
                    />
                </div>
                <div className={"flex flex-1 flex-col space-y-2 pr-2"}>
                    <div className={"flex flex-row justify-between items-center space-x-2"}>
                        <p className="font-bold line-clamp-1">{film.title}</p>
                        <X
                            className="text-white mt-1 hover:scale-110 hover:text-orange-500 flex-shrink-0"
                            size={22}
                            strokeWidth={3}
                            onClick={(e) => {
                                e.stopPropagation();
                                e.preventDefault();
                                onDelete(film.id);
                            }}
                        />
                    </div>
                    <p className="line-clamp-2 text-sm opacity-60">{film.synopsis}</p>
                    <p className="line-clamp-1 text-sm">Genres: {film.genres.map(g => g.name).join(', ')}</p>
                </div>

            </div>
        </Link>
    )
}

function slug(film : SearchList): string {
    return film.title.toLowerCase().split(" ").join("-")+'-'+film.id
}