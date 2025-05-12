import {NextRequest, NextResponse} from "next/server";
import {url} from "@/types/cons";
import {cookies} from "next/headers";
import {UserService} from "@/utils/user-service";

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

    const loginRes = await fetch(url + '/v1/auth/login', options);
    const data = (await loginRes.json());
    console.log(data);
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

    const userId = await UserService.getUserId(data.data.email);
    (await cookies()).set("userId", userId, {
        maxAge: 24 * 60 * 60,
        sameSite: "strict"
    });

    return NextResponse.json(data.data, { status: 201 });
}
