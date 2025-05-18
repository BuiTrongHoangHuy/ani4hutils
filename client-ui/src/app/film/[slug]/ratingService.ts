import { Rating, RatingRequest } from "@/types/rating";
import { url2 } from "@/types/cons";

const baseUrl = `${url2}/v1/rating`;

export interface RatingResponse {
    data: Rating;
}

export const RatingService = {

    getRatingByUserIdAndFilmId: async (userId: string, filmId: string): Promise<RatingResponse | null> => {
        try {
            const response = await fetch(`${baseUrl}/user/${userId}/film/${filmId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                },
            });
            
            if (response.status === 404) {
                return null;
            }
            
            if (!response.ok) {
                throw new Error(`Error fetching rating: ${response.statusText}`);
            }
            
            return response.json();
        } catch (error) {
            console.error("Error fetching rating:", error);
            return null;
        }
    },
    
    /**
     * Add or update a rating
     * @param rating The rating request
     * @returns True if successful, false otherwise
     */
    upsertRating: async (rating: RatingRequest): Promise<boolean> => {
        try {
            const response = await fetch(`${baseUrl}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(rating)
            });
            
            return response.ok;
        } catch (error) {
            console.error("Error upserting rating:", error);
            return false;
        }
    }
};