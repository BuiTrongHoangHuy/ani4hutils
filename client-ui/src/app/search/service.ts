import {PagingSearch} from "@/types/search/pagingSearch";
import {BuildQueryParams} from "@/utils/build-query-params";
import {Paging} from "@/types/paging";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = 'https://api.ani4h.site/v1/search';
const baseUrl2 = 'https://api.ani4h.site';


export const SearchService = {
    search: async (title: string, paging: PagingSearch) => {
        if (!title) {
            return { data: [] };
        }

        const params = BuildQueryParams(paging);
        const url = `${baseUrl}?title=${title}&${params}`;

        const res= await fetchWithInterceptor(url, {
            method: 'GET',
        });
        return res.json();
    },
    /*filter: async (data: any, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        const dataParams = BuildQueryParams(data);
        return await fetchWithCredentials(`${baseUrl}?${pagingParams}&${dataParams}`, {
            method: 'GET',
        });
    },*/
    contentBasedRecommendation: async (filmId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl}/content-based?filmId=${filmId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        })).json();
    },
    userFavoriteRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl}/user-favorite?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        })).json();
    },
    userHistoryRecommendation: async (userId: string, seed: number, paging: PagingSearch) => {
        const pagingParams = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl}/user-history?userId=${userId}&seed=${seed}&${pagingParams}`, {
            method: 'GET',
        })).json();
    },
    getTopHot: async (paging: Paging) => {
        const pagingParams = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl2}/v1/film/top-hot?${pagingParams}`, {
            method: 'GET',
        })).json();
    }
}