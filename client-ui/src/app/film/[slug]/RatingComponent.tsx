"use client";

import { useState, useEffect } from "react";
import { RatingService } from "./ratingService";
import { RatingRequest } from "@/types/rating";
import { Star } from "lucide-react";
import {setupClientInterceptors} from "@/utils/interceptorClient";

interface RatingComponentProps {
    userId: string;
    filmId: string;
    className?: string;
}

export default function RatingComponent({ userId, filmId, className = "" }: RatingComponentProps) {
    const [rating, setRating] = useState<number | null>(null);
    const [hoveredRating, setHoveredRating] = useState<number | null>(null);
    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        const fetchRating = async () => {
            if (!userId || !filmId) {
                setLoading(false);
                return;
            }

            try {
                setLoading(true);
                setupClientInterceptors()
                const userRating = await RatingService.getRatingByUserIdAndFilmId(userId, filmId);
                if (userRating) {
                    setRating(userRating.data.rating);
                    console.log("User rating:", userRating);
                }
            } catch (error) {
                console.error("Error fetching rating:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchRating();
    }, [userId, filmId]);

    const handleRatingClick = async (newRating: number) => {
        if (!userId || submitting) return;

        try {
            setSubmitting(true);
            const ratingRequest: RatingRequest = {
                userId,
                filmId,
                rating: newRating
            };

            const success = await RatingService.upsertRating(ratingRequest);
            if (success) {
                setRating(newRating);
            }
        } catch (error) {
            console.error("Error submitting rating:", error);
        } finally {
            setSubmitting(false);
        }
    };

    const renderStars = () => {
        const stars = [];
        const maxRating = 5;
        const displayRating = hoveredRating !== null ? hoveredRating : rating;

        for (let i = 1; i <= maxRating; i++) {
            const filled = displayRating !== null && i <= displayRating;

            stars.push(
                <button
                    key={i}
                    className={`text-2xl ${filled ? "text-yellow-400" : "text-gray-400"} hover:text-yellow-400 transition-colors`}
                    onMouseEnter={() => setHoveredRating(i)}
                    onMouseLeave={() => setHoveredRating(null)}
                    onClick={() => handleRatingClick(i)}
                    disabled={submitting}
                    aria-label={`Rate ${i} out of 5 stars`}
                >
                    <Star size={24} fill={filled ? "currentColor" : "none"} />
                </button>
            );
        }

        return stars;
    };

    if (loading) {
        return <div className={`flex items-center space-x-1 ${className}`}>Loading rating...</div>;
    }

    if (!userId) {
        return <div className={`flex items-center space-x-1 ${className}`}>Sign in to rate this film</div>;
    }

    return (
        <div className={`flex flex-col ${className}`}>
            <div className="text-sm font-semibold mb-1">Rate this film:</div>
            <div className="flex space-x-1">
                {renderStars()}
            </div>
            {rating && (
                <div className="text-sm mt-1">
                    Your rating: {rating}/5
                </div>
            )}
        </div>
    );
}
