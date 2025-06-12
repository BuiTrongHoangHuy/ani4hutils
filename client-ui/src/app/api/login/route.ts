import {NextRequest, NextResponse} from "next/server";
import {url} from "@/types/cons";
import {cookies} from "next/headers";
import {createServerFetch} from "@/utils/interceptorServer";
import {fetchWithInterceptor} from "@/utils/fetchWithInterceptor";

export async function POST(request: NextRequest) {
    const body = await request.json();
    const options: RequestInit & { duplex: 'half' } = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
        duplex: 'half',
    };

    createServerFetch("")

    const loginRes = await fetchWithInterceptor(url + '/v1/auth/login', options);
    const data = (await loginRes.json());
    console.log("access token save", data.data.accessToken);
    (await cookies()).set("accessToken", data.data.accessToken, {
        httpOnly: true,
        maxAge: 24 * 60 * 60,
        sameSite: "strict"
    });
    (await cookies()).set("email", data.data.email, {
        httpOnly: true,
        maxAge: 24 * 60 * 60,
        sameSite: "strict"
    });
    //const userId = await UserService.getUserId(data.data.email);
    const options2: RequestInit & { duplex: 'half' } = {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            authorization: `Bearer ${data.data.accessToken}`,
        },
        duplex: 'half',

    };
    const getRes = await fetch(url + '/v1/user/get-by-email?email=' + data.data.email, options2)
    const userId = (await getRes.json()).data;
    (await cookies()).set("userId", userId, {
        maxAge: 24 * 60 * 60,
        sameSite: "strict"
    });

    return NextResponse.json(data.data, { status: 201 });
}
