import React from "react";
import Image from "next/image";
import ProfileSideBar from "@/components/ProfileSideBar";

export default async function ProfileLayout(
    {
        children,
        params
    }: Readonly<{
        children: React.ReactNode;
        params: Promise<{ slug: string }>
    }>
) {
    const { slug } = await params

    return (
        <div className={"w-screen mt-[64px] pl-20 pr-20 pt-8"}>
            <div className={"flex flex-row w-full h-full space-x-8"}>
                <div className={"flex flex-col w-75 h-96 space-y-4"}>

                    <div className={"flex flex-col h-96 w-full justify-center items-center space-y-4"}>
                        <div className={"relative w-40 aspect-square"}>
                            <Image
                                className="rounded-full object-cover"
                                fill={true}
                                src={"https://cdn.myanimelist.net/images/anime/1448/147351.jpg"}
                                alt={"Profile"}
                            />
                        </div>
                        <p className={"font-bold text-2xl"}>Bùi Thái Hoàng</p>
                    </div>

                    <ProfileSideBar id={slug}/>

                </div>

                <div className={"flex-1"}>
                    {children}
                </div>
            </div>
        </div>
    )
}