import {PagingSearch} from "@/types/search/pagingSearch";
import {BuildQueryParams} from "@/utils/build-query-params";

const baseUrl = 'http://localhost:4003/v1/search';

export const SearchService = {
    search: async (title: string, paging: PagingSearch) => {
        const params = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}?title=${title}&${params}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
    filter: async (data: any, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const dataParams = BuildQueryParams(data);

        const res = await fetch(`${baseUrl}?${pagingParams}&${dataParams}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return res.json();
    },
    contentBasedRecommendation: async (filmId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}/content-based?filmId=${filmId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
    userFavoriteRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}/user-favorite?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
    userHistoryRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}/user-history?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
}