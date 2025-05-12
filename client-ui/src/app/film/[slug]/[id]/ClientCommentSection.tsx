"use client";

import dynamic from 'next/dynamic';
import { Suspense } from 'react';

// Dynamically import the CommentSection component
const CommentSection = dynamic(() => import('./CommentSection'), {
  loading: () => <div className="text-center py-4">Loading comments section...</div>,
});

interface ClientCommentSectionProps {
  filmId: string;
  episodeId?: string;
}

export default function ClientCommentSection({ filmId, episodeId }: ClientCommentSectionProps) {
  return (
    <Suspense fallback={<div className="text-center py-4">Loading comments section...</div>}>
      <CommentSection filmId={filmId} episodeId={episodeId} />
    </Suspense>
  );
}