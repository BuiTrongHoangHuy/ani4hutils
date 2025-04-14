import {Image} from "@/types/image";

export interface SearchList{
    id: string,
    title: string,
    images?: Image[],
    genres : string[],
    synopsis: string,
    year?: number,
    views?: number,
    avgStar?: number,
    maxEpisodes: number,
    numEpisodes: number,
}