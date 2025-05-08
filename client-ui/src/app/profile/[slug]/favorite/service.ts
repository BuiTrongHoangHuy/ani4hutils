export const FavoriteService = {
    addFavorite: async (userId: string, filmId: string) => {
        const res = await fetch(`http://localhost:4002/v1/favorite/add`, {
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
        const res = await fetch(`http://localhost:4002/v1/favorite/remove?userId=${userId}&filmId=${filmId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    },

    checkFavorite: async (userId: string, filmId: string) => {
        const res = await fetch(`http://localhost:4002/v1/favorite/is-favorite?userId=${userId}&filmId=${filmId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    },

    getFavorites: async (userId: string, paging: Paging) => {
        const params = buildQueryParams(paging);
        const res = await fetch(`http://localhost:4002/v1/favorite/list?userId=${userId}&${params}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return res.json();
    }
}

function buildQueryParams(paging: Paging) {
    const params = new URLSearchParams();
    if (paging.cursor) {
        params.append("cursor", paging.cursor);
    }
    if (paging.page) {
        params.append("page", paging.page.toString());
    }
    if (paging.pageSize) {
        params.append("pageSize", paging.pageSize.toString());
    }
    return params.toString();
}
