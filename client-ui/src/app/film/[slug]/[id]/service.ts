import {BuildQueryParams} from "@/utils/build-query-params";

const baseUrl ="http://localhost:4002/v1/film"
const baseUrlEpisode = "http://localhost:4002/v1/episode"
export const FilmService = {
    getById: async (id: string) => {
        const response = await fetch(`${baseUrl}/${id}`,{
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        })
        return response.json()
    },

    getEpisodesById: async (id: string) => {
        const response = await fetch(`${baseUrl}/${id}/episodes`,{
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        })
        return response.json()
    },

    getEpisodeByEpisodeNumber: async (id: string, numberEpisode: number) => {
        const userId = "3w5rMJ7r2JjRwM"
        const request = {
            userId: userId,
            filmId: id,
            numberEpisode: numberEpisode,
        }
        const params = BuildQueryParams(request)
        const response = await fetch(`${baseUrlEpisode}?${params}`,{
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        })
        return response.json()
    }

}