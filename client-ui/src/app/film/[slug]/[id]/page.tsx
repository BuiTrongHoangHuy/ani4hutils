import PlayerWrapper from "@/app/film/[slug]/[id]/PlayerWrapper";
import {FilmService} from "@/app/film/[slug]/[id]/service";
import {Film} from "@/types/film";
import {Episode} from "@/types/episode";
import Link from "next/link";

export default async function  Page({
                                  params,
                              }: {
    params: Promise<{ id: string, slug:string }>
}) {
    const { id } = await params
    const {slug} = await params
    const filmId = slug.split('-')[slug.split('-').length-1]
    const data = await FilmService.getById(filmId)
    const filmData : Film = await data.data
    const episodes = await FilmService.getEpisodesById(filmId)
    const episodesData:Episode[] = await episodes.data
    const currentEpisode = parseInt(id.split('-')[id.split('-').length-1])


    console.log( "film",filmData)
    console.log( "film",slug.split('-')[slug.split('-').length-1])
    console.log( "episodes", episodes)
    console.log(id.split('-')[id.split('-').length-1])
    return (
        <div className={"w-screen h-screen mt-[64px] px-20 py-10 space-y-20 flex flex-col gap-10"}>
            <div className={"flex flex-row w-full h-full"}>
                <div className={"flex-2"}>
                    <PlayerWrapper filmId={filmId} episodeNumber={currentEpisode}></PlayerWrapper>
                </div>
                <div className={"flex-1"}>
                    <div className={"w-full h-full"}>
                        <div className={"px-4 text-2xl font-bold"}>
                            {filmData?.title}
                        </div>
                        <div className="collapse collapse-arrow bg-base-100 border-base-300 border">
                            <input type="checkbox"/>
                            <div className="collapse-title font-semibold">Playlist</div>
                            <div className="collapse-content text-sm ">
                                <div id="grid" className="grid grid-cols-5 gap-4 p-4 mx-auto overflow-y-auto max-h-[400px]">
                                    {
                                        episodesData.map((value, index) =>
                                            <Link key={index} className={`btn btn-ghost w-auto h-16 rounded-sm text-lg
                                         bg-[#ffffff1a] ${currentEpisode === value.episodeNumber ? 'text-primary' : 'text-white'} `}
                                                  href={`/film/${slug}/episode-${value.episodeNumber}`}
                                            >
                                                {value.episodeNumber}
                                            </Link>
                                        )
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className={"flex flex-row w-full h-full"}>
                <div className={"flex-1"}>
                    <div className={"w-full h-full"}>
                        <div className={"px-4 text-2xl font-bold"}>
                            {filmData?.title}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}