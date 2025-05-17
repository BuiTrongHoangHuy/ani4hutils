export interface Comment {
    id: number;
    filmId: string;
    userId: string;
    episodeId?: string;
    content: string;
    displayName: string;
    createdAt: string;
    updatedAt: string;
}

export interface CommentRequest {
    filmId: string;
    userId: string;
    episodeId?: string;
    content: string;
}