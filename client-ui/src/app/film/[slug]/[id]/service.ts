import {BuildQueryParams} from "@/utils/build-query-params";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl ="https://api.ani4h.site/v1/film"
const baseUrlEpisode = "https://api.ani4h.site/v1/episode"
export const FilmService = {
    getById: async (id: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/${id}`, {
            method: "GET",
        })).json();
    },

    getEpisodesById: async (id: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/${id}/episodes`, {
            method: "GET",
        })).json()
    },

    getEpisodeByEpisodeNumber: async (id: string, numberEpisode: number) => {
        const userId = "3w5rMJ7r2JjRwM"
        const request = {
            userId: userId,
            filmId: id,
            numberEpisode: numberEpisode,
        }
        const params = BuildQueryParams(request)
        return await (await fetchWithInterceptor(`${baseUrlEpisode}?${params}`, {
            method: "GET",
        })).json();
    }

}
