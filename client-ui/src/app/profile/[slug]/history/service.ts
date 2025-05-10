import {WatchHistoryRequest} from "@/types/watch-history-request";
import {BuildQueryParams} from "@/utils/build-query-params";

const baseUrl = 'http://localhost:4002/v1/watch-history';

export const HistoryService = {
    saveHistory: async (req: WatchHistoryRequest) => {
        const res = await fetch(`${baseUrl}/upsert`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(req),
        });
        return res.json();
    },
    isWatched: async (userId: string, episodeId: string) => {
        const res = await fetch(`${baseUrl}/is-watched?userId=${userId}&episodeId=${episodeId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
    deleteHistory: async (userId: string, episodeId: string) => {
        const res = await fetch(`${baseUrl}/delete?userId=${userId}&episodeId=${episodeId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    },
    getWatchedEpisodes: async (userId: string, paging: Paging) => {
        const params = BuildQueryParams(paging);
        const res = await fetch(`${baseUrl}?userId=${userId}&${params}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return res.json();
    }
}