import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

const baseUrl = "https://api.ani4h.site/v1/user";

export const UserService = {
    getUserId: async (email: string) => {
        if (!email) return null;

        const res = await fetchWithInterceptor(`${baseUrl}/get-by-email?email=${email}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });


        const result = await res.json();

        console.log("dasd",result);
        const userId = result.userId || null;
        return userId;
    }
}