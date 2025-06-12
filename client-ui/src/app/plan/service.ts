import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

export interface SubscriptionPlan {
    id: number;
    name: string;
    price: number;
    quality: 'Fair' | 'Good' | 'Excellent';
    resolution: '480p' | '720p' | '1080p' | '2K + HDR' | '4K + HDR';
    maxSimultaneousStreams: number;
}

export interface SubscriptionResponse{
    data: SubscriptionPlan[];
};

export interface PaymentRequest {
    userId: string;
    price: number;
}

export interface PaymentResponse {
    data:{
        urlPayment: string;
    }
}
const baseUrl = "https://api.ani4h.site/v1";

export const getSubscriptionPlans = async (): Promise<SubscriptionResponse> => {
    const response = await fetchWithInterceptor(`${baseUrl}/subscription`,
        {
            method: 'GET',
        }
        );
    return response.json();
};

export const createPayment = async (request: PaymentRequest): Promise<PaymentResponse> => {
    const response = await fetchWithInterceptor(`${baseUrl}/payment/create`,
        {
            method: 'POST',
            body: JSON.stringify(request),
            headers: {
                'Content-Type': 'application/json',
            }
        });
    return response.json();
};

export const getPaymentStatus = async (userId: string): Promise<{data:[{
        id: string,
        name: string,
        price: string,
        quality: string,
    }]}> => {
    const response = await fetchWithInterceptor(`${baseUrl}/user/${userId}/subscription`,
        {
            method: 'GET',
        });
    return response.json();
};