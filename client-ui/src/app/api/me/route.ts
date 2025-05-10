import {NextRequest, NextResponse} from "next/server";

export function GET(request: NextRequest) {
    const accessToken = request.cookies.get('accessToken')?.value
    const email = request.cookies.get('email')?.value
    if (!accessToken || !email) {
        return NextResponse.json({ loggedIn: false }, { status: 401 })
    }
    return NextResponse.json({
        loggedIn: true,
        email: email,
    },{status: 200})
}