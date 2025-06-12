import {BuildQueryParams} from "@/utils/build-query-params";
import {Paging} from "@/types/paging";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = "https://api.ani4h.site/v1/favorite";

export const FavoriteService = {
    addFavorite: async (userId: string, filmId: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/add`, {
            method: "POST",
            body: JSON.stringify({
                userId: userId,
                filmId: filmId,
            }),
        })).json();
    },

    removeFavorite: async (userId: string, filmId: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/remove?userId=${userId}&filmId=${filmId}`, {
            method: "DELETE",
        })).json();
    },

    checkFavorite: async (userId: string, filmId: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/is-favorite?userId=${userId}&filmId=${filmId}`, {
            method: "GET",
        })).json();
    },

    getFavorites: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl}/list?userId=${userId}&${params}`, {
            method: "GET",
        })).json();
    }
}
