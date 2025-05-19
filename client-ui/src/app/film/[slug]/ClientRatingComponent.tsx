"use client";

import dynamic from 'next/dynamic';
import { Suspense } from 'react';

const RatingComponent = dynamic(() => import('./RatingComponent'), {
  loading: () => <div className="text-center py-2">Loading rating component...</div>,
});

interface ClientRatingComponentProps {
  userId: string;
  filmId: string;
  className?: string;
}

export default function ClientRatingComponent({ userId, filmId, className }: ClientRatingComponentProps) {
  return (
    <Suspense fallback={<div className="text-center py-2">Loading rating component...</div>}>
      <RatingComponent userId={userId} filmId={filmId} className={className} />
    </Suspense>
  );
}