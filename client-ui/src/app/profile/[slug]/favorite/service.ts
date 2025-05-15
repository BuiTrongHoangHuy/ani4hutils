import {BuildQueryParams} from "@/utils/build-query-params";
import {fetchWithCredentials} from "@/utils/fetch-with-credentials";

const baseUrl = "http://localhost:4002/v1/favorite";

export const FavoriteService = {
    addFavorite: async (userId: string, filmId: string) => {
        return await fetchWithCredentials(`${baseUrl}/add`, {
            method: "POST",
            body: JSON.stringify({
                userId: userId,
                filmId: filmId,
            }),
        });
    },

    removeFavorite: async (userId: string, filmId: string) => {
        return await fetchWithCredentials(`${baseUrl}/remove?userId=${userId}&filmId=${filmId}`, {
            method: "DELETE",
        });
    },

    checkFavorite: async (userId: string, filmId: string) => {
        return await fetchWithCredentials(`${baseUrl}/is-favorite?userId=${userId}&filmId=${filmId}`, {
            method: "GET",
        });
    },

    getFavorites: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        return await fetchWithCredentials(`${baseUrl}/list?userId=${userId}&${params}`, {
            method: "GET",
        });
    }
}
