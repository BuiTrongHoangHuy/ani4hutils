"use client";

import React, { useState, useEffect } from "react";
import { Comment, CommentRequest } from "@/types/comment";
import {CommentResponse, CommentService, PagingParams} from "./commentService";

interface CommentSectionProps {
    filmId: string;
    episodeId?: string;
}

export default function CommentSection({ filmId, episodeId }: CommentSectionProps) {
    const [comments, setComments] = useState<Comment[]>([]);
    const [newComment, setNewComment] = useState("");
    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [userId, setUserId] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);

    useEffect(() => {
        fetch("/api/me", {
            method: "GET"
        }).then(response => {
            if (response.status === 200) {
                response.json().then(data => {
                    if (data.userId) {
                        setUserId(data.userId);
                    }
                });
            }
        }).catch(error => {
            console.error("Error fetching user ID:", error);
        });
    }, []);

    useEffect(() => {
        const fetchComments = async () => {
            try {
                setLoading(true);
                let fetchedComments: CommentResponse;

                const paging: PagingParams = {
                    page: currentPage,
                    pageSize: pageSize
                };

                if (episodeId) {
                    fetchedComments = await CommentService.getCommentsByEpisodeId(episodeId, paging);
                } else {
                    fetchedComments = await CommentService.getCommentsByFilmId(filmId, paging);
                }

                setComments(fetchedComments.data);
            } catch (error) {
                console.error("Error fetching comments:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchComments();
    }, [filmId, episodeId, currentPage, pageSize]);

    const handleSubmitComment = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!newComment.trim()) return;

        try {
            setSubmitting(true);

            const commentRequest: CommentRequest = {
                filmId,
                userId,
                content: newComment,
                episodeId
            };

            await CommentService.addComment(commentRequest);

            setNewComment("");

            setCurrentPage(1);

            const paging: PagingParams = {
                page: 1,
                pageSize: pageSize
            };

            if (episodeId) {
                const refreshedComments = await CommentService.getCommentsByEpisodeId(episodeId, paging);
                console.log("refreshedComments:", refreshedComments);
                setComments(refreshedComments.data);
            } else {
                const refreshedComments = await CommentService.getCommentsByFilmId(filmId, paging);
                console.log("refreshedComments:", refreshedComments);
                setComments(refreshedComments.data);
            }
        } catch (error) {
            console.error("Error adding comment:", error);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="w-full space-y-6">
            <div className="px-4 text-2xl font-bold">
                Comments
            </div>

            <form onSubmit={handleSubmitComment} className="px-4 space-y-2">
                <textarea
                    className="w-full p-3 bg-base-200 rounded-md text-white min-h-20 max-h-50"
                    rows={3}
                    placeholder="Add a comment..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    disabled={submitting}
                />
                <div className="flex justify-end">
                    <button
                        type="submit"
                        className="btn btn-primary"
                        disabled={submitting || !newComment.trim()}
                    >
                        {submitting ? "Posting..." : "Post Comment"}
                    </button>
                </div>
            </form>

            <div className="px-4 space-y-4">
                {loading ? (
                    <div className="text-center py-4">Loading comments...</div>
                ) : (
                    <>
                        {comments && comments.map((comment) => (
                            <div key={comment.id} className="p-4 bg-base-200 rounded-md">
                                <div className="flex justify-between items-start">
                                    <div className="font-semibold">{comment.displayName}</div>
                                    <div className="text-xs text-gray-400">
                                        {new Date(comment.createdAt).toLocaleDateString()}
                                    </div>
                                </div>
                                <div className="mt-2">{comment.content}</div>
                            </div>
                        ))}

                        <div className="flex justify-center items-center space-x-4 mt-6">
                            <button 
                                className="btn btn-sm btn-outline" 
                                onClick={() => setCurrentPage(prev => Math.max(1, prev - 1))}
                                disabled={currentPage === 1 || loading}
                            >
                                Previous
                            </button>
                            <span className="text-sm">Page {currentPage}</span>
                            <button 
                                className="btn btn-sm btn-outline" 
                                onClick={() => setCurrentPage(prev => prev + 1)}
                                disabled={comments && comments.length < pageSize || loading}
                            >
                                Next
                            </button>

                            <select 
                                className="select select-sm select-bordered ml-4"
                                value={pageSize}
                                onChange={(e) => {
                                    setPageSize(Number(e.target.value));
                                    setCurrentPage(1);
                                }}
                            >
                                <option value="5">5 per page</option>
                                <option value="10">10 per page</option>
                            </select>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}
