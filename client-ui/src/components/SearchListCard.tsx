import {SearchList} from "@/types/searchList";
import Link from "next/link";
import Image from "next/image";
import {IconsMetadata} from "next/dist/lib/metadata/generate/icons";

export default function SearchListCard({film}: {film: SearchList}) {
    return(
        <Link href={`/film/${slug(film)}`}>
            <div className={"flex flex-row hover:scale-103 transition-transform duration-300 w-full h-72"}>
                <div className="w-52 h-full relative">
                    <Image
                        className="rounded-md object-cover"
                        fill={true}
                        src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                        alt={film.title}
                    />
                </div>
                <div className={"flex flex-1 flex-col h-full pt-2 pb-2 pr-6 pl-6 space-y-1"}>
                    <p className="text-xl font-bold">
                        {film.title}
                        <span className="font-normal opacity-50"> ({film.year})</span>
                    </p>
                    <p className="line-clamp-4 opacity-60">{film.synopsis}</p>
                    <p className="font-normal">Genres: {film.genres.join(", ")}</p>
                    <p className="font-normal">Views: {film.views?.toLocaleString()}</p>
                    <p className="font-normal">Score: {film.avgStar}/10</p>
                    <p className="font-normal">Episodes: {film.numEpisodes}/{film.maxEpisodes}</p>
                </div>
            </div>
        </Link>
    )
}

function slug(film : SearchList): string {
    return film.title.toLowerCase().split(" ").join("-")+'-'+film.id
}