// interceptorConfig.ts
import {
    addRequestInterceptor,
    addResponseInterceptor,
} from "./fetchWithInterceptor";

export function setupInterceptors(): void {
    addRequestInterceptor((url, options) => {
        const token = sessionStorage.getItem("accessToken");
        return {
            url,
            options: {
                ...options,
                headers: {
                    ...options.headers,
                    Authorization: token ? `Bearer ${token}` : "",
                },
            },
        };
    });

    addResponseInterceptor((response) => {
        if (response.status === 401) {
            console.warn("Unauthorized access – consider redirecting to login.");
            // Redirect or logout logic here
        }
        return response;
    });
}
