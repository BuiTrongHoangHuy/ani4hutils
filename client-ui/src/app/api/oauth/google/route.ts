import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

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

    const loginRes = await fetch('https://api.ani4h.com/v1/auth/google-oauth', options);
    const data = (await loginRes.json());
    console.log(data);
    (await cookies()).set("accessToken", data.data.accessToken, {
        httpOnly: true,
        maxAge: 24 * 60 * 60,
        sameSite: "strict"
    });

    return NextResponse.json(data.data, { status: 200 });
}