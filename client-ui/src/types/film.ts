import {Image} from "@/types/image";
import {genre} from "@/types/genre";
import {Character} from "@/types/character";
import {Producer} from "@/types/producer";
import {Studio} from "@/types/studio";

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
    characters?: Character[],
    producers?: Producer[],
    studios?: Studio[],
    view?: number,
    year?: number,
    season?: string,
    startDate? : Date,
    endDate? : Date
    images?: Image[]
}
