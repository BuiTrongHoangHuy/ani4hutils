import {Image} from "@/types/image";

export interface film {
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
    view?: number,
    year: number,
    season: string,
    startDate? : Date,
    endDate? : Date
    images?: Image[]
}