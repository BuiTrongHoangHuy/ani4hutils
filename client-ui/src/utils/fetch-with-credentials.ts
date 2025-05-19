export const fetchWithCredentials = async (url: string, options: RequestInit = {}) => {
    try {
        const res = await fetch(url, {
            ...options,
            credentials: 'include', // 👈 để gửi cookie (bao gồm token dạng HttpOnly)
            headers: {
                'Content-Type': 'application/json',
                ...(options.headers || {}), // giữ lại các headers khác nếu có
            },
        });

        if (!res.ok) {
            console.warn(`Server responded with status: ${res.status}`);
            return { data: [] };
        }

        const json = await res.json();
        return json || { data: [] };
    } catch (error) {
        console.error('Fetch failed:', error);
        return { data: [] };
    }
};
