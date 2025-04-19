import {Image} from "@/types/image";
import {genre} from "@/types/genre";

export interface SearchList{
    id: string,
    title: string,
    images?: Image[],
    genres : genre[],
    synopsis: string,
    year?: number,
    views?: number,
    avgStar?: number,
    maxEpisodes: number,
    numEpisodes: number,
}
