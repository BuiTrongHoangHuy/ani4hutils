// utils/interceptorServer.ts

import {
    addRequestInterceptor,
    fetchWithInterceptor,
} from './fetchWithInterceptor';

export function createServerFetch(token: string) {
    addRequestInterceptor((url, options) => ({
        url,
        options: {
            ...options,
            headers: {
                ...options.headers,
                Authorization: `Bearer ${token}`,
            },
        },
    }));

    return fetchWithInterceptor;
}
