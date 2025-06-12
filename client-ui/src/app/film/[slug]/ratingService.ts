import { Rating, RatingRequest } from "@/types/rating";
import { url2 } from "@/types/cons";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = `${url2}/v1/rating`;

export interface RatingResponse {
    data: Rating;
}

export const RatingService = {

    getRatingByUserIdAndFilmId: async (userId: string, filmId: string): Promise<RatingResponse | null> => {
        try {
            const response = await fetchWithInterceptor(`${baseUrl}/user/${userId}/film/${filmId}`, {
                method: "GET",
            });

            if (response.status === 404) {
                return null;
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
            const response = await fetchWithInterceptor(`${baseUrl}`, {
                method: "POST",
                body: JSON.stringify(rating),
            });

            return response && response.status !== undefined && response.status < 400;
        } catch (error) {
            console.error("Error upserting rating:", error);
            return false;
        }
    }
};
