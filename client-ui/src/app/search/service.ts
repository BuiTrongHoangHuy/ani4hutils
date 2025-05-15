import {PagingSearch} from "@/types/search/pagingSearch";
import {BuildQueryParams} from "@/utils/build-query-params";
import { fetchWithCredentials } from "@/utils/fetch-with-credentials";

const baseUrl = 'http://localhost:4002/v1/search';

export const SearchService = {
    search: async (title: string, paging: PagingSearch) => {
        if (!title) {
            return { data: [] };
        }

        const params = BuildQueryParams(paging);
        const url = `${baseUrl}?title=${title}&${params}`;

        return await fetchWithCredentials(url, {
            method: 'GET',
        });
    },
    filter: async (data: any, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const dataParams = BuildQueryParams(data);
        return await fetchWithCredentials(`${baseUrl}?${pagingParams}&${dataParams}`, {
            method: 'GET',
        });
    },
    contentBasedRecommendation: async (filmId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await fetchWithCredentials(`${baseUrl}/content-based?filmId=${filmId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        });
    },
    userFavoriteRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await fetchWithCredentials(`${baseUrl}/user-favorite?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        });
    },
    userHistoryRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await fetchWithCredentials(`${baseUrl}/user-history?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        });
    },
    getTopHot: async (paging: Paging) => {
        const pagingParams = BuildQueryParams(paging);
        return await fetchWithCredentials(`http://localhost:4002/v1/film/top-hot?${pagingParams}`, {
            method: 'GET',
        });
    }
}