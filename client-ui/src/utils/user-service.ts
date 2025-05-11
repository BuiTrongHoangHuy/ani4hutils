const baseUrl = "http://localhost:4001/v1/user";

export const UserService = {
    getUserId: async (email: string) => {
        if (!email) return null;

        const res = await fetch(`${baseUrl}/get-by-email?email=${email}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        const result = await res.json();
        const userId = result.data;
        return userId;
    }
}