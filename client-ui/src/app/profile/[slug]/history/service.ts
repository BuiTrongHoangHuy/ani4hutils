import {WatchHistoryRequest} from "@/types/watch-history-request";
import {BuildQueryParams} from "@/utils/build-query-params";
import {Paging} from "@/types/paging";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = 'https://api.ani4h.site/v1/watch-history';

export const HistoryService = {
    saveHistory: async (req: WatchHistoryRequest) => {
        return await (await fetchWithInterceptor(`${baseUrl}/upsert`, {
            method: 'POST',
            body: JSON.stringify(req),
        })).json();
    },
    isWatched: async (userId: string, episodeId: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/is-watched?userId=${userId}&episodeId=${episodeId}`, {
            method: 'GET',
        })).json();
    },
    deleteHistory: async (userId: string, episodeId: string) => {
        return await (await fetchWithInterceptor(`${baseUrl}/delete?userId=${userId}&episodeId=${episodeId}`, {
            method: 'DELETE',
        })).json();
    },
    getWatchedEpisodes: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        return await (await fetchWithInterceptor(`${baseUrl}?userId=${userId}&${params}`, {
            method: 'GET',
        })).json();
    }
}