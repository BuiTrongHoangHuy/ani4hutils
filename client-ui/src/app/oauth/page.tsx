'use client'
import {useEffect, useState} from "react";

export default  function Page() {
    const [accessToken, setAccessToken] = useState<string | null>(null);
    useEffect(() => {
        if (typeof window !== 'undefined') {
            const hash = window.location.hash.substring(1);
            const params = new URLSearchParams(hash);
            const token = params.get('access_token');
            if (token) {
                setAccessToken(token);
                console.log('Access token:', token);
            }
        }
    }, []);

    return (
        <div>
            <h1>OAuth Redirect</h1>
            {accessToken ? (
                <p>Access Token: {accessToken}</p>
            ) : (
                <p>Đang xử lý token...</p>
            )}
        </div>
    );
}