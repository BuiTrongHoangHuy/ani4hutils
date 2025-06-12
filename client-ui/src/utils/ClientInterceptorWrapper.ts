'use client';
import { useEffect } from 'react';
import { setupClientInterceptors } from '@/utils/interceptorClient';

export function ClientInterceptorWrapper() {
    useEffect(() => {
        setupClientInterceptors();
    }, []);

    return null;
}
