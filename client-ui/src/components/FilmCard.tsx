import {SearchList} from "@/types/searchList";
import Link from "next/link";
import Image from "next/image";

interface FilmCardProps {
    film: SearchList;
    row?: boolean;
    height?: number;
    width?: number;
    fontSize?: number;
}

export default function FilmCard(
    {
        film,
        row = false,
        height = 40,
        width = 30,
        fontSize = 16
    }: FilmCardProps
) {
    return (
        <Link href={`/film/${slug(film)}`}>
            {row?
                <div className={"flex flex-row cursor-pointer hover:scale-103 duration-200 items-center space-x-2"}>
                    <div
                        className="space-y-3 relative" style={{height: height*4, width: width*4}}>
                        <Image
                            className="rounded-md object-cover"
                            fill={true}
                            src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                            alt={film.title}
                        />
                    </div>
                    <p className="line-clamp-1" style={{fontSize: fontSize}}>{film.title}</p>
                </div>
                :
                <div className={"flex flex-col cursor-pointer hover:scale-103 transition-transform duration-200"} style={{width: width*4}}>
                    <div
                        className="space-y-3 relative" style={{height: height*4, width: width*4}}>
                        <Image
                            className="rounded-md object-cover"
                            fill={true}
                            src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                            alt={film.title}
                        />
                    </div>
                    <p className="line-clamp-2" style={{fontSize: fontSize}}>{film.title}</p>
                </div>
            }
        </Link>
    )
}

function slug(film : SearchList): string {
    return film.title.toLowerCase().split(" ").join("-")+'-'+film.id
}