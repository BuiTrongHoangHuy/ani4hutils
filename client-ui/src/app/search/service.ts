import {PagingSearch} from "@/types/search/pagingSearch";
import {BuildQueryParams} from "@/utils/build-query-params";

const baseUrl = 'http://localhost:4002/v1/search';

export const SearchService = {
    search: async (title: string, paging: PagingSearch) => {
        if (!title) {
            return { data: [] };
        }

        const userId = getCookie('userId');
        console.log("sdkjfhjkds sdhgfjdsf hsdjkfhdsk hsdjkfjdksf dsjfhjdsf "+userId);


        const params = BuildQueryParams(paging);
        try{
            const res = await fetch(`${baseUrl}?title=${title}&${params}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }

            const json = await res.json();
            return json || { data: [] };
        }
        catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }
    },
    filter: async (data: any, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const dataParams = BuildQueryParams(data);
        try {
            const res = await fetch(`${baseUrl}?${pagingParams}&${dataParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }

            const json = await res.json();
            return json || { data: [] };
        }
        catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }
    },
    contentBasedRecommendation: async (filmId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        try {
            const res = await fetch(`${baseUrl}/content-based?filmId=${filmId}&seed=${seed}&${pagingParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }
            const json = await res.json();
            return json || { data: [] };
        }
        catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }

    },
    userFavoriteRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        try {
            const res = await fetch(`${baseUrl}/user-favorite?userId=${userId}&seed=${seed}&${pagingParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }

            const json = await res.json();
            return json || { data: [] };
        }
        catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }
    },
    userHistoryRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        try{
            const res = await fetch(`${baseUrl}/user-history?userId=${userId}&seed=${seed}&${pagingParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }

            const json = await res.json();
            return json || { data: [] };
        }
        catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }
    },
    getTopHot: async (paging: Paging) => {
        const pagingParams = BuildQueryParams(paging);
        try {
            const res = await fetch(`http://localhost:4002/v1/film/top-hot?${pagingParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                console.warn(`Server responded with status: ${res.status}`);
                return { data: [] };
            }

            const json = await res.json();
            return json || { data: [] };
        } catch (error) {
            console.error('Fetch failed (server có thể chưa bật?):', error);
            return { data: [] };
        }
    }
}


function getCookie(name: string): string | null {
    const cookieString = document.cookie;
    const cookies = cookieString.split('; ');

    for (const cookie of cookies) {
        const [key, value] = cookie.split('=');
        if (key === name) return decodeURIComponent(value);
    }

    return null;
}