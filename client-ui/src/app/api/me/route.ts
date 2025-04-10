import {NextRequest, NextResponse} from "next/server";

export function GET(request: NextRequest) {
    const accessToken = request.cookies.get('accessToken')?.value
    if (!accessToken) {
        return NextResponse.json({ loggedIn: false }, { status: 401 })
    }
    return NextResponse.json({
        loggedIn: true,

    },{status: 200})
}