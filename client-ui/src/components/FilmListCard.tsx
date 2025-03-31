import {FilmList} from "@/types/filmList";
import Image from "next/image";
import Link from "next/link";

export default function FilmListCard({film} : {film: FilmList}) {
    return (
        <Link href={`/${slug(film)}`}>
            <div className={"cursor-pointer hover:scale-110 transition-transform duration-300 "}>
                <div
                    className="w-64 h-80 space-y-3 relative">
                    <Image
                        className="rounded-md object-cover"
                        fill={true}
                        src={film.images?.[0]?.url || ""}
                        alt={film.title}
                    />
                </div>
                <p className="line-clamp-1 opacity-50">{film.title}</p>
            </div>
        </Link>
    )
}

function slug(film : FilmList): string {
    return film.title.toLowerCase().replace(" ","-")+'-'+film.id
}

