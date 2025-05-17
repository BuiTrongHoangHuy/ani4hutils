'use client'
import dynamic from "next/dynamic";

const Player = dynamic(() => import('@/app/film/[slug]/[id]/player'), { ssr: false, loading: () => <div className="h-full w-full flex justify-center items-center"
    ><div className="loading loading-spinner loading-xl"></div></div>
});

export default function PlayerWrapper({filmId, episodeNumber}: { filmId: string, episodeNumber:number}) {
    return <Player filmId={filmId} episodeNumber={episodeNumber}></Player>
}