import {Image} from "@/types/image";

export interface FilmList {
    id: string,
    title: string,
    maxEpisodes: number,
    numEpisodes: number,
    state: string,
    series?: {
      id: string,
      name: string,
    },
    images?: Image[]
}