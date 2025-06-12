// utils/fetchWithInterceptor.ts

export type FetchOptions = RequestInit;

export type RequestInterceptor = (
    url: string,
    options: FetchOptions
) => Promise<{ url?: string; options?: FetchOptions }> | { url?: string; options?: FetchOptions };

export type ResponseInterceptor = (
    response: Response
) => Promise<Response | void> | Response | void;

const requestInterceptors: RequestInterceptor[] = [];
const responseInterceptors: ResponseInterceptor[] = [];

export function addRequestInterceptor(interceptor: RequestInterceptor): void {
    requestInterceptors.push(interceptor);
}

export function addResponseInterceptor(interceptor: ResponseInterceptor): void {
    responseInterceptors.push(interceptor);
}

export async function fetchWithInterceptor(
    url: string,
    options: FetchOptions = {}
): Promise<Response> {
    let finalUrl = url;
    let finalOptions = { ...options };

    for (const interceptor of requestInterceptors) {
        const result = await interceptor(finalUrl, finalOptions);
        if (result.url) finalUrl = result.url;
        if (result.options) finalOptions = result.options;
    }

    let response = await fetch(finalUrl, finalOptions);

    for (const interceptor of responseInterceptors) {
        const modifiedResponse = await interceptor(response);
        if (modifiedResponse instanceof Response) {
            response = modifiedResponse;
        }
    }

    return response;
}
