import { Comment, CommentRequest } from "@/types/comment";

const baseUrl = "http://localhost:4002/v1/comments";

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

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });
        return response.json();
    },

    getCommentsByEpisodeId: async (episodeId: string, paging?: PagingParams): Promise<CommentResponse> => {
        const params = new URLSearchParams();
        if (paging?.page) params.append('page', paging.page.toString());
        if (paging?.pageSize) params.append('pageSize', paging.pageSize.toString());

        const url = `${baseUrl}/episode/${episodeId}${params.toString() ? `?${params.toString()}` : ''}`;

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
        });
        return response.json();
    },

    addComment: async (comment: CommentRequest): Promise<void> => {
        await fetch(`${baseUrl}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(comment)
        });
    },

    updateComment: async (commentId: number, comment: CommentRequest): Promise<void> => {
        await fetch(`${baseUrl}/${commentId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(comment)
        });
    },

    deleteComment: async (commentId: number): Promise<void> => {
        await fetch(`${baseUrl}/${commentId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
        });
    }
};
