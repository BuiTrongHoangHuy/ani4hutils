import Image from "next/image";
import {film} from "@/types/film";
import {FilmList} from "@/types/filmList";
import ListFilm from "@/components/ListFilm";
import Link from "next/link";
import {url} from "@/types/cons";

export default async function Page({
                                       params,
                                   }: {
    params: Promise<{ slug: string }>
}) {
    const { slug } = await params
    const data = await fetch(`${url}/v1/film/${slug.split('-')[slug.split('-').length-1]}`)
    const filmData : film = (await data.json()).data
    const filmsData = await fetch(`${url}/v1/film`)
    const films : FilmList[] = (await filmsData.json()).data
    return (
        <div className={"w-screen mt-[64px] px-20 py-10 space-y-20 "}>
            <div className={"flex justify-between"}>
                <div
                    className="w-[400px] h-[500px] space-y-3 relative">
                    <Image
                        className="rounded-md object-fill"
                        fill={true}
                        src={filmData?.images?.[0]?.url || "https://placehold.co/300x400/png?text=ani4h.site"}
                        alt={filmData?.title}
                    />
                </div>
                <div className={"space-y-4  w-2/3 font-bold"}>
                    <h1 className={"text-3xl font-bold"}>{filmData?.title}</h1>
                    <div className={"flex space-x-2"}>
                        <Image src={`/images/icons/start.svg`} alt={""} width="16" height="16"/>
                        <p className={"text-primary"}>{filmData.avgStar || 0}</p>
                        <div className={"divider divider-horizontal"}></div>
                        {filmData.season && <p>{filmData.season}</p>}
                        <p>{filmData.year || new Date(filmData.startDate || Date.now()).getUTCFullYear()}</p>
                        <div className={"divider divider-horizontal"}></div>
                        <p>Update to {filmData.numEpisodes} episodes</p>
                        <div className={"divider divider-horizontal"}></div>
                        <p>Full {filmData.maxEpisodes} episodes</p>
                    </div>
                    <div className={"flex space-x-2"}>
                        <p>Category: </p>
                        {filmData.genres.map((g, i) =>
                            <Link key={i} href={`/film?genre=${g.id}`}>{g.name}</Link>
                        )}
                    </div>
                    <p className={"font-bold"}>{filmData?.synopsis}</p>
                    <p>Views: {filmData.view || 0}</p>
                    <div className={"flex w-full justify-end space-x-5"}>
                        <Link href={`/film/${slug}/episode-1`} className={"btn btn-primary text-2xl px-8 py-6"}>
                            <Image src={`/images/icons/play.png`} alt={""} width="16" height="16"/>
                            Transmit
                        </Link>
                        <div className={"btn bg-gray-500 hover:gray-300 text-2xl px-8 py-6"}>
                            <Image src={`/images/icons/plus.svg`} alt={""} width="24" height="24"/>
                            Save
                        </div>
                    </div>
                </div>
            </div>
            <div className={"flex space-x-4"}>
                <Link href={`/${slug}`} className={"btn btn-primary p-6 text-xl rounded-md"}>Part 1</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 2</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 3</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 4</Link>
            </div>
            <ListFilm title={"Proposal for you"} films={films} className={"px-0!"}></ListFilm>
        </div>

    )
}

