import {FilmList} from "@/types/filmList";
import Image from "next/image";

export default function FilmListCard({film} : {film: FilmList}) {
    return (
        <div className={"w-48 h-72 space-y-2"}>
            <Image className={"rounded-md"} width={400} height={600} src={film.image?.url || ""} alt={film.title}/>
            <p className={"line-clamp-1 opacity-50"}>{film.title}</p>
        </div>
    )
}