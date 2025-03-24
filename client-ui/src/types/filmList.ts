import {Image} from "@/types/image";

export interface FilmList {
    id: string,
    title: string,
    maxEpisode: number,
    numEpisode: number,
    state: string,
    series?: {
      id: string,
      name: string,
    },
    image?: Image
}