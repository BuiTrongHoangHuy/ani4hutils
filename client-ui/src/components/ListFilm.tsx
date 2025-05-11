import {FilmList} from "@/types/filmList";
import FilmListCard from "@/components/FilmListCard";

export default function ListFilm({title, films,className} : {title?: string,films: FilmList[],className? :string}) {
    return (
        <div className={`w-full space-y-8 px-10  ${className}`}>
            <p className={"text-2xl font-bold"}>{title}</p>
            <ul className="flex space-x-10 w-full z-[50] pb-5 overflow-x-scroll overflow-y-hidden">
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