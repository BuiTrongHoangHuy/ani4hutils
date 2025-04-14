'use client'
import {useEffect, useState} from "react";
import {FullScreenLoading} from "@/components/FullScreenLoading";
import {useRouter, useSearchParams} from "next/navigation";

export default function Page() {
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const searchParams = useSearchParams()
    const provider = searchParams.get("provider")
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
    const router = useRouter()
    useEffect(() => {
        fetch(`api/oauth?provider=${provider}`, {
            method: "POST",
            body: JSON.stringify({
                token: accessToken
            })
        }).then(r => {
            // if (r.status == 200) {
            //     // router.push("/")
            // }
        }).catch(
            e => {
                console.error(e)
            }
        )
    }, [accessToken, router]);
    return (
        <div>
            <FullScreenLoading isLoading={true}/>
            <div className={"h-screen w-screen"}>
            </div>
        </div>
    );
}