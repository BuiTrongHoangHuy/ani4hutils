import {FilmList} from "@/types/filmList";
import FilmListCard from "@/components/FilmListCard";

export default function ListFilm({title, films} : {title?: string,films: FilmList[]}) {
    return (
        <div className={"w-full space-y-4 px-10"}>
            <p className={"text-2xl font-bold"}>{title}</p>
            <ul className="flex space-x-10 w-full z-[50]">
                {films.map((film, index) => {
                    return (
                        <li key={index}>
                            <FilmListCard film={film}/>
                        </li>
                    )
                })}
            </ul>
        </div>
    )
}