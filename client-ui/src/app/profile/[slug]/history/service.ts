import {WatchHistoryRequest} from "@/types/watch-history-request";
import {BuildQueryParams} from "@/utils/build-query-params";
import {fetchWithCredentials} from "@/utils/fetch-with-credentials";
import {Paging} from "@/types/paging";

const baseUrl = 'http://localhost:4002/v1/watch-history';

export const HistoryService = {
    saveHistory: async (req: WatchHistoryRequest) => {
        return await fetchWithCredentials(`${baseUrl}/upsert`, {
            method: 'POST',
            body: JSON.stringify(req),
        });
    },
    isWatched: async (userId: string, episodeId: string) => {
        return await fetchWithCredentials(`${baseUrl}/is-watched?userId=${userId}&episodeId=${episodeId}`, {
            method: 'GET',
        });
    },
    deleteHistory: async (userId: string, episodeId: string) => {
        return await fetchWithCredentials(`${baseUrl}/delete?userId=${userId}&episodeId=${episodeId}`, {
            method: 'DELETE',
        });
    },
    getWatchedEpisodes: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        return await fetchWithCredentials(`${baseUrl}?userId=${userId}&${params}`, {
            method: 'GET',
        });
    }
}