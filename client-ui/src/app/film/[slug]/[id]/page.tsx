import PlayerWrapper from "@/app/film/[slug]/[id]/PlayerWrapper";
import {FilmService} from "@/app/film/[slug]/[id]/service";
import {Film} from "@/types/film";
import {Episode} from "@/types/episode";
import Link from "next/link";
import ClientCommentSection from "./ClientCommentSection";
import FilmCard from "@/components/FilmCard";
import {SearchList} from "@/types/search/searchList";
import {SearchService} from "@/app/search/service";
import {PagingSearch} from "@/types/search/pagingSearch";
import {Paging} from "@/types/paging";
import {FilmList} from "@/types/filmList";
import {cookies} from "next/headers";
import FilmListCardSM from "@/app/film/[slug]/[id]/filmListCardSM";
import Image from "next/image";
import React from "react";
import {createServerFetch} from "@/utils/interceptorServer";

export default async function  Page({
                                  params,
                              }: {
    params: Promise<{ id: string, slug:string }>
}) {
    const { id } = await params
    const {slug} = await params
    const cookieStore = await cookies()
    const accessToken = cookieStore.get('accessToken')?.value || ""
    createServerFetch(accessToken);
    const filmId = slug.split('-')[slug.split('-').length-1]
    const data = await FilmService.getById(filmId)
    const filmData : Film = await data.data
    const episodes = await FilmService.getEpisodesById(filmId)
    const episodesData:Episode[] = await episodes.data
    const currentEpisode = parseInt(id.split('-')[id.split('-').length-1])
    const episode = await FilmService.getEpisodeByEpisodeNumber(filmId, currentEpisode)
    const episodeData : Episode = await episode.data
    const newPaging: Paging = {
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 10,
    }
    const paging: PagingSearch = {
        cursor: "",
        page: 1,
        pageSize: 8,
    }
    const userId = cookieStore.get('userId')?.value || ""
    const resFavorite = await SearchService.userFavoriteRecommendation(userId,0, paging)
    const favorites : FilmList[] = (await resFavorite.data.data || [])
    const topHot = await SearchService.getTopHot(newPaging)
    const topHotResponse: SearchList[] = await topHot.data
    return (
        <div className={"w-full h-full mt-[64px] px-20 py-10 space-y-20 flex flex-col gap-10"}>
            <div className={"flex flex-row w-full h-full gap-10 min-h-[480px]"}>
                <div className={"flex-2"}>
                    <PlayerWrapper filmId={filmId} episodeNumber={currentEpisode}></PlayerWrapper>
                </div>
                <div className={"flex-1"}>
                    <div className={"w-full h-full"}>
                        <div className={"px-4 text-2xl font-bold"}>
                            {filmData?.title}
                        </div>
                        <div>
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
            </div>
            <div className={"flex flex-row w-full h-full gap-10"}>
                <div className={"flex-2"}>
                    <div className={"flex flex-col w-full h-full gap-5"}>
                        { filmData && (
                            <div className="px-4 mb-8 bg-base-200 rounded-md p-4">
                                <div className="flex gap-4">
                                    <div className="relative w-[100px] h-[150px]">
                                        <Image
                                            className="rounded-md object-cover"
                                            fill={true}
                                            src={filmData?.images?.[0]?.url || "https://placehold.co/300x400/png?text=ani4h.site"}
                                            alt={filmData?.title}
                                        />
                                    </div>
                                    <div className="flex-1">
                                        <h2 className="text-2xl font-bold mb-2">{filmData.title}</h2>
                                        <div className="flex items-center gap-2 mb-2">
                                            <span className="text-primary">{filmData.avgStar || 0} ★</span>
                                            <span>•</span>
                                            <span>{filmData.year || new Date(filmData.startDate || Date.now()).getUTCFullYear()}</span>
                                            <span>•</span>
                                            <span>{filmData.numEpisodes}/{filmData.maxEpisodes} episodes</span>
                                        </div>
                                        <div className="mb-2">
                                            <span className="font-semibold">Genres: </span>
                                            {filmData.genres && filmData.genres.map((genre, i) => (
                                                <span key={i} className="mr-2">{genre.name}</span>
                                            ))}
                                        </div>
                                        <p className="text-sm line-clamp-3">{filmData.synopsis}</p>
                                        {filmData.view !== undefined && (
                                            <p className="text-sm mt-2">Views: {filmData.view}</p>
                                        )}
                                    </div>
                                </div>
                            </div>
                        )}
                        <ClientCommentSection
                            filmId={filmId}
                            episodeId={episodeData?.id}
                        />

                        <div className={"flex flex-col"}>
                            <p className={"px-4 text-2xl font-bold"}>Proposal for you</p>
                            <div id="grid" className="grid grid-cols-4 gap-4 p-4 mx-auto ">
                                {
                                    favorites && favorites.map((value, index) =>
                                        <div key={index}>
                                            <FilmListCardSM film={value}/>
                                        </div>
                                    )
                                }
                            </div>
                        </div>

                    </div>
                </div>
                <div className={"flex-1"}>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Hottest of the day</p>
                        {topHotResponse && topHotResponse.map((film, index) => {
                            let rankColor = "text-gray-400";
                            if (index === 0) rankColor = "text-orange-500";
                            else if (index === 1) rankColor = "text-orange-400";
                            else if (index === 2) rankColor = "text-orange-300";

                            return (
                                <div key={index} className="flex flex-row items-center space-x-2">
                                    <p className={`text-xl font-bold ${rankColor}`}>{index + 1}</p>
                                    <FilmCard film={film} row={true} height={20} width={16} fontSize={14} />
                                </div>
                            );
                        })}
                    </div>
                </div>
                </div>

            </div>
    )
}
