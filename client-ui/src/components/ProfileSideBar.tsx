'use client'
import {BookHeart, History, LogOut, UserRound} from "lucide-react";
import Link from "next/link";
import React from "react";
import {usePathname} from "next/navigation";


export default function ProfileSideBar(
    {id} : {id: string}
){
    const pathName = usePathname()

    const menuItems = [
        {
            label: "Personal Information",
            icon: <UserRound strokeWidth={3} size={24} />,
            href: `/profile/${id}`
        },
        {
            label: "Favorite",
            icon: <BookHeart strokeWidth={3} size={24} />,
            href: `/profile/${id}/favorite`
        },
        {
            label: "History",
            icon: <History strokeWidth={3} size={24} />,
            href: `/profile/${id}/history`
        },
        {
            label: "Log out",
            icon: <LogOut strokeWidth={3} size={24} />,
            href: `/logout`
        }
    ]

    return (
        <div className={"flex flex-col space-y-4 p-4"} style={{background: "#FFFFFF1A"}}>
            {menuItems.map((item, i) =>
                <div key={i} className={"flex flex-col space-y-4"}>
                    <Link href={item.href}>
                        <div className={`flex space-x-2 ${pathName === item.href ? "text-orange-500" : "text-white"} items-center`}>
                            {item.icon}
                            <p className={"font-bold "}>{item.label}</p>
                        </div>
                    </Link>
                    {(i !== menuItems.length - 1) && <hr className={"border-gray-500"} />}
                </div>
            )}
        </div>
    )
}