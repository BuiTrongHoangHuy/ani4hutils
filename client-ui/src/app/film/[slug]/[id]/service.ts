
const baseUrl ="http://localhost:4002/v1/film"

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
        const response = await fetch(`${baseUrl}/${id}/episode/${numberEpisode}`,{
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        })
        return response.json()
    }

}