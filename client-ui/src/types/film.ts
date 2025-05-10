import {Image} from "@/types/image";
import {genre} from "@/types/genre";

export interface Film {
    id: string,
    synopsis: string,
    title: string,
    maxEpisodes: number,
    numEpisodes: number,
    avgStar?: number
    state: string,
    series?: {
        id: string,
        name: string,
    },
    genres: genre[],
    view?: number,
    year: number,
    season: string,
    startDate? : Date,
    endDate? : Date
    images?: Image[]
}

