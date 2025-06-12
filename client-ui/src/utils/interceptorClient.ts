'use client';
import { addRequestInterceptor, addResponseInterceptor } from './fetchWithInterceptor';

let clientInterceptorInitialized = false;

export function setupClientInterceptors(): void {
    if (typeof window === 'undefined' || clientInterceptorInitialized) return;
    clientInterceptorInitialized = true;

    addRequestInterceptor((url, options) => {
        const token = sessionStorage.getItem('accessToken');
        console.log("access tokennnnnnnnnn", token);
        return {
            url,
            options: {
                ...options,
                headers: {
                    ...options.headers,
                    Authorization: token ? `Bearer ${token}` : '',
                },
            },
        };
    });

    addResponseInterceptor((response) => {
        if (response.status === 401) {
            console.warn('Unauthorized – maybe redirect to login?');
        }
        return response;
    });
}
