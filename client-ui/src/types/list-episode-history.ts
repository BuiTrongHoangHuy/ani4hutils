import {Image} from "@/types/image";

export interface ListEpisodeHistory {
    id: string;
    title: string;
    episodeNumber: number;
    synopsis: string;
    duration: number;
    thumbnail: Image;
    viewCount: number;
    watchedDuration: number;
    filmId: string;
    filmTitle: string;
}