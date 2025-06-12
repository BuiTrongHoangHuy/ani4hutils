import { Comment, CommentRequest } from "@/types/comment";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = "https://api.ani4h.site/v1/comments";

export interface CommentResponse {
    data: Comment[];
}

export interface PagingParams {
    page?: number;
    pageSize?: number;
}

export const CommentService = {
    getCommentsByFilmId: async (filmId: string, paging?: PagingParams): Promise<CommentResponse> => {
        const params = new URLSearchParams();
        if (paging?.page) params.append('page', paging.page.toString());
        if (paging?.pageSize) params.append('pageSize', paging.pageSize.toString());

        const url = `${baseUrl}/film/${filmId}${params.toString() ? `?${params.toString()}` : ''}`;

        return await (await fetchWithInterceptor(url, {
            method: "GET",
        })).json();
    },

    getCommentsByEpisodeId: async (episodeId: string, paging?: PagingParams): Promise<CommentResponse> => {
        const params = new URLSearchParams();
        if (paging?.page) params.append('page', paging.page.toString());
        if (paging?.pageSize) params.append('pageSize', paging.pageSize.toString());

        const url = `${baseUrl}/episode/${episodeId}${params.toString() ? `?${params.toString()}` : ''}`;

        return await (await fetchWithInterceptor(url, {
            method: "GET",
        })).json();
    },

    addComment: async (comment: CommentRequest): Promise<void> => {
        await (await fetchWithInterceptor(`${baseUrl}`, {
            method: "POST",
            body: JSON.stringify(comment)
        })).json();
    },

    updateComment: async (commentId: number, comment: CommentRequest): Promise<void> => {
        await (await fetchWithInterceptor(`${baseUrl}/${commentId}`, {
            method: "PUT",
            body: JSON.stringify(comment)
        })).json();
    },

    deleteComment: async (commentId: number): Promise<void> => {
        await (await fetchWithInterceptor(`${baseUrl}/${commentId}`, {
            method: "DELETE",
        })).json();
    }
};
