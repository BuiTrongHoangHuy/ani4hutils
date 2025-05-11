import {NextRequest, NextResponse} from "next/server";

export function GET(request: NextRequest) {
    const accessToken = request.cookies.get('accessToken')?.value
    const email = request.cookies.get('email')?.value
    const userId = request.cookies.get('userId')?.value
    if (!accessToken || !email) {
        return NextResponse.json({ loggedIn: false }, { status: 401 })
    }
    return NextResponse.json({
        loggedIn: true,
        email: email,
        userId: userId,
    },{status: 200})
}