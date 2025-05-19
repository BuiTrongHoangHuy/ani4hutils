export interface Rating {
    id: string;
    userId: string;
    filmId: string;
    rating: number;
    createdAt: string;
    updatedAt: string;
}

export interface RatingRequest {
    userId: string;
    filmId: string;
    rating: number;
}