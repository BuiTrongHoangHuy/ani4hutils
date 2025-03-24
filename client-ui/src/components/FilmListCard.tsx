import {FilmList} from "@/types/filmList";
import Image from "next/image";

export default function FilmListCard({film} : {film: FilmList}) {
    return (
        <div className="w-48 h-72 space-y-3 cursor-pointer group">
            <Image
                className="rounded-md group-hover:scale-110 transition-transform duration-300"
                width={400}
                height={600}
                src={film.image?.url || ""}
                alt={film.title}
            />
            <p className="line-clamp-1 opacity-50">{film.title}</p>
        </div>
    )
}