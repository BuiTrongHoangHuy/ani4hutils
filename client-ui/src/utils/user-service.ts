const baseUrl = "http://localhost:4001/v1/user";

export const UserService = {
    getUserId: async (email: string) => {
        const res = await fetch(`${baseUrl}/get-by-email?email=${email}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        const result = await res.json();
        const userId = result.data;
        localStorage.setItem("userId", userId);
        return userId;
    }
}