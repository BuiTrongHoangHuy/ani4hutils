import {BuildQueryParams} from "@/utils/build-query-params";
import { fetchWithCredentials } from "@/utils/fetch-with-credentials";

const baseUrl ="https://api.ani4h.com/v1/film"
const baseUrlEpisode = "https://api.ani4h.com/v1/episode"
export const FilmService = {
    getById: async (id: string) => {
        return await fetchWithCredentials(`${baseUrl}/${id}`, {
            method: "GET",
        })
    },

    getEpisodesById: async (id: string) => {
        return await fetchWithCredentials(`${baseUrl}/${id}/episodes`, {
            method: "GET",
        })
    },

    getEpisodeByEpisodeNumber: async (id: string, numberEpisode: number) => {
        const userId = "3w5rMJ7r2JjRwM"
        const request = {
            userId: userId,
            filmId: id,
            numberEpisode: numberEpisode,
        }
        const params = BuildQueryParams(request)
        return await fetchWithCredentials(`${baseUrlEpisode}?${params}`, {
            method: "GET",
        })
    }

}
