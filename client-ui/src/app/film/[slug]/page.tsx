import Image from "next/image";
import {Film} from "@/types/film";
import Link from "next/link";
import {url2} from "@/types/cons";
import {SearchService} from "@/app/search/service";
import {PagingSearch} from "@/types/search/pagingSearch";
import {cookies} from "next/headers";
import ButtonFavorite from "@/components/ButtonFavorite";
import {SearchList} from "@/types/search/searchList";
import FilmCard from "@/components/FilmCard";
import ClientRatingComponent from "./ClientRatingComponent";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";
import {createServerFetch} from "@/utils/interceptorServer";

export default async function Page(
    {
       params,
    }:
    {
        params: Promise<{ slug: string }>
    }) {
    const { slug } = await params
    const filmId = slug.split('-')[slug.split('-').length-1]
    const data = await (await fetchWithInterceptor(`${url2}/v1/film/${filmId}`, {
        method: "GET"
    })).json();
    const filmData : Film = data.data
    const seed = Math.floor(Math.random() * 1000)
    const cookieStore = await cookies()
    const userId = cookieStore.get('userId')?.value || ""
    const accessToken = cookieStore.get('accessToken')?.value || ""
    createServerFetch(accessToken);

    const paging: PagingSearch = {
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 12,
    }
    const resContentBase= await SearchService.contentBasedRecommendation(filmId, seed, paging) || []
    const films : SearchList[] = resContentBase.data.data || []
    return (
        <div className={"w-screen mt-16 px-20 py-10 space-y-20 "}>
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
                    <div className={"flex space-x-2 flex-wrap gap-2 text-gray-400"}>
                        <Image src={`/images/icons/start.svg`} alt={""} width="16" height="16"/>
                        <p className={"text-primary"}>{filmData?.avgStar || 0}</p>
                        <div className={"divider divider-horizontal"}></div>
                        {filmData?.season && <p>{filmData.season}</p>}
                        <p>{filmData?.year || new Date(filmData?.startDate || Date.now()).getUTCFullYear()}</p>
                        <div className={"divider divider-horizontal"}></div>
                        <p>Update to {filmData.numEpisodes} episodes</p>
                        <div className={"divider divider-horizontal"}></div>
                        <p>Full {filmData.maxEpisodes} episodes</p>
                    </div>
                    <ClientRatingComponent userId={userId} filmId={filmId} className="mt-2" />
                    <div className="flex flex-wrap items-center gap-2 text-sm text-gray-400">
                        <p className="font-semibold">Genres:</p>
                        {filmData.genres && filmData.genres.map((g, i) => (
                            <Link
                                key={i}
                                href={``}
                                className="px-2 py-1 bg-primary text-primary-content rounded hover:bg-primary/50 text-xs"
                            >
                                {g.name}
                            </Link>
                        ))}
                    </div>
                    <p className={"font-bold text-gray-400"}>{filmData?.synopsis}</p>
                    <p className="text-gray-400">Views: {filmData?.view || 0}</p>
                    <div className={"flex w-full gap-4 pt-4"}>
                        <Link href={`/film/${slug}/episode-1`} className={"btn bg-orange-600 btn-primary text-2xl px-8 py-6"}>
                            <Image src={`/images/icons/play.png`} alt={""} width="16" height="16"/>
                            Watch
                        </Link>
                        <ButtonFavorite userId={userId} filmId={filmId}/>
                    </div>
                    <div className="collapse collapse-arrow bg-base-100 border-base-300 border">
                        <input type="checkbox"/>
                        <div className="collapse-title font-bold text-gray-400 px-0">Characters & Cast</div>
                        <div className="collapse-content text-sm ">
                            <div className="gap-4 mx-auto overflow-y-auto max-h-[400px]">
                                {
                                    filmData.characters && filmData.characters.length > 0 && (
                                        <div className="mt-4">
                                            <div className="grid grid-cols-4 gap-4">
                                                {filmData.characters.map((character, index) => (
                                                    <div key={index} className="bg-base-200 p-3 rounded-md">
                                                        <div className="flex items-center gap-3">
                                                            {(
                                                                <div className="relative w-[50px] h-[50px]">
                                                                    <Image
                                                                        className="rounded-full object-cover"
                                                                        fill={true}
                                                                        src={character?.image?.url||"https://placehold.co/300x400/png?text=ani4h.site"}
                                                                        alt={character.name}
                                                                    />
                                                                </div>
                                                            )}
                                                            <div>
                                                                <p className="font-bold">{character.name}</p>
                                                                <p className="text-sm text-gray-400">{character.role}</p>
                                                            </div>
                                                        </div>

                                                        {character.actors && character.actors.length > 0 && (
                                                            <div className="mt-2 pl-4 border-l-2 border-gray-700">
                                                                <p className="text-sm text-gray-400 mb-1">Voiced by:</p>
                                                                {character.actors.map((actor, idx) => (
                                                                    <div key={idx} className="flex items-center gap-2 mb-1">
                                                                        { (
                                                                            <div className="relative w-[30px] h-[30px]">
                                                                                <Image
                                                                                    className="rounded-full object-cover"
                                                                                    fill={true}
                                                                                    src={actor?.image?.url || "https://placehold.co/300x400/png?text=ani4h.site"}
                                                                                    alt={actor.name}
                                                                                />
                                                                            </div>
                                                                        )}
                                                                        <div>
                                                                            <p className="text-sm">{actor.name}</p>
                                                                            <p className="text-xs text-gray-400">{actor.language}</p>
                                                                        </div>
                                                                    </div>
                                                                ))}
                                                            </div>
                                                        )}
                                                    </div>
                                                ))}
                                            </div>
                                        </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>

                    <div className="collapse collapse-arrow bg-base-100 border-base-300 border">
                        <input type="checkbox"/>
                        <div className="collapse-title font-bold text-gray-400 px-0">Producers</div>
                        <div className="collapse-content text-sm ">
                            <div className="gap-4 mx-auto overflow-y-auto max-h-[400px]">
                                {
                                    filmData.producers && filmData.producers.length > 0 && (
                                        <div className="mt-4">
                                            <div className="grid grid-cols-3 gap-4">
                                                {filmData.producers.map((producer, index) => (
                                                    <div key={index} className="bg-base-200 p-3 rounded-md">
                                                        <div className="flex items-center gap-3">
                                                            {(
                                                                <div className="relative w-[50px] h-[50px]">
                                                                    <Image
                                                                        className="rounded-full object-cover"
                                                                        fill={true}
                                                                        src={producer?.image?.url || "https://placehold.co/300x400/png?text=ani4h.site"}
                                                                        alt={producer.name}
                                                                    />
                                                                </div>
                                                            )}
                                                            <div>
                                                                <p className="font-bold">{producer.name}</p>
                                                                {producer.description && (
                                                                    <p className="text-sm text-gray-400">{producer.description}</p>
                                                                )}
                                                            </div>
                                                        </div>
                                                    </div>
                                                ))}
                                            </div>
                                        </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>

                    <div className="collapse collapse-arrow bg-base-100 border-base-300 border">
                        <input type="checkbox"/>
                        <div className="collapse-title font-bold text-gray-400 px-0">Studios</div>
                        <div className="collapse-content text-sm ">
                            <div className="gap-4 mx-auto overflow-y-auto max-h-[400px]">
                                {
                                    filmData.studios && filmData.studios.length > 0 && (
                                        <div className="mt-4">
                                            <div className="grid grid-cols-3 gap-4">
                                                {filmData.studios.map((studio, index) => (
                                                    <div key={index} className="bg-base-200 p-3 rounded-md">
                                                        <div className="flex items-center gap-3">
                                                            {(
                                                                <div className="relative w-[50px] h-[50px]">
                                                                    <Image
                                                                        className="rounded-full object-cover"
                                                                        fill={true}
                                                                        src={studio?.image?.url || "https://placehold.co/300x400/png?text=ani4h.site"}
                                                                        alt={studio.name}
                                                                    />
                                                                </div>
                                                            )}
                                                            <div>
                                                                <p className="font-bold">{studio.name}</p>
                                                                {studio.description && (
                                                                    <p className="text-sm text-gray-400">{studio.description}</p>
                                                                )}
                                                            </div>
                                                        </div>
                                                    </div>
                                                ))}
                                            </div>
                                        </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            {/*<div className={"flex space-x-4"}>
                <Link href={`/${slug}`} className={"btn btn-primary p-6 text-xl rounded-md"}>Part 1</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 2</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 3</Link>
                <Link href={`/${slug}`} className={"btn bg-gray-500 hover:gray-300 p-6 text-xl rounded-md"}>Part 4</Link>
            </div>*/}
            <div className={"flex flex-col space-y-8"}>
                <p className={"text-2xl font-bold"}>Proposal for you</p>
                <div className={"grid grid-cols-6 gap-8 grid-flow-row auto-rows-fr"}>
                    {
                        films && films.map((film, i) =>
                            <FilmCard key={i} film={film} height={64} width={48} fontSize={16}/>
                        )
                    }
                </div>
            </div>
        </div>

    )
}
