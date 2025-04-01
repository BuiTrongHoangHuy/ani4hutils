import axiosInstance from "../../axiosConfig.ts";

export  interface Authenticate {
    email: string,
    password: string
}

export interface AuthenticationResponseData {
    accessToken: {
        token: string;
    };
    refreshToken: {
        token: string;
    };
}

interface AuthenticationResponse {
    data: AuthenticationResponseData;
}

export async function login(
    body: Authenticate,
): Promise<AuthenticationResponse> {
    try {
        const response = await axiosInstance.post<AuthenticationResponse>(
            "v1/auth/login",
            body,
        );
        return response.data;
    } catch (error) {
        console.error("Fetch error:", error);
        throw error;
    }
}