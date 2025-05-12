import {FilmList} from "@/types/filmList";
import Image from "next/image";
import Link from "next/link";

export default function FilmListCardSM({film} : {film: FilmList}) {
    return (
        <Link href={`/film/${slug(film)}`}>
            <div className={"flex flex-col items-center space-y-2 cursor-pointer hover:scale-105 transition-transform duration-300 "}>
                <div
                    className="w-48 h-60 space-y-3 relative">
                    <Image
                        className="rounded-md object-cover"
                        fill={true}
                        src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                        alt={film.title}
                    />
                </div>
                <p className="line-clamp-1 font-medium">{film.title}</p>
            </div>
        </Link>
    )
}

function slug(film : FilmList): string {
    return film.title.toLowerCase().split(" ").join("-")+'-'+film.id
}

