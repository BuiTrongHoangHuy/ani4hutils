'use client'
import dynamic from "next/dynamic";

const Player = dynamic(() => import('@/app/film/[slug]/[id]/player'), { ssr: false });

export default function PlayerWrapper({filmId, episodeNumber}: { filmId: string, episodeNumber:number}) {
    if (!filmId || !episodeNumber) return <div>Loading...</div>;
    return <Player filmId={filmId} episodeNumber={episodeNumber}></Player>
}