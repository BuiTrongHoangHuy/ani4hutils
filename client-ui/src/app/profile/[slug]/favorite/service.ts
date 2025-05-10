import {BuildQueryParams} from "@/utils/build-query-params";

const baseUrl = "http://localhost:4002/v1/favorite";

export const FavoriteService = {
    addFavorite: async (userId: string, filmId: string) => {
        const res = await fetch(`${baseUrl}/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userId: userId,
                filmId: filmId,
            }),
        });
        return res.json();
    },

    removeFavorite: async (userId: string, filmId: string) => {
        const res = await fetch(`${baseUrl}/remove?userId=${userId}&filmId=${filmId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    },

    checkFavorite: async (userId: string, filmId: string) => {
        const res = await fetch(`${baseUrl}/is-favorite?userId=${userId}&filmId=${filmId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    },

    getFavorites: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}/list?userId=${userId}&${params}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    }
}
