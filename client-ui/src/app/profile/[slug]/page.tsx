'use client'

import Image from "next/image";
import React from "react";

export default function ProfilePage(){


    return (
        <div className={"flex flex-col w-full h-full space-y-8"}>
            <div className={"flex flex-col pl-20 pr-20 pt-10 pb-10 space-y-8"} style={{background: "#FFFFFF1A"}}>
                <div className={"flex justify-between"}>
                    <p className={"font-bold text-orange-500"}>Personal Information</p>
                    <p className={"font-bold text-orange-500"}>Edit Profile</p>
                </div>

                <div className={"flex items-center space-x-42"}>
                    <p>Avatar</p>
                    <div className={"relative w-20 aspect-square"}>
                        <Image
                            className="rounded-full object-cover"
                            fill={true}
                            src={"https://cdn.myanimelist.net/images/anime/1448/147351.jpg"}
                            alt={"Profile"}
                        />
                    </div>
                </div>

                <div className={"flex flex-row space-x-12"}>
                    <div className={"flex flex-col w-40 space-y-8"}>
                        <p>Name</p>
                        <p>Date of birth</p>
                        <p>Sex</p>
                        <p>Email contact</p>
                    </div>

                    <div className={"flex flex-col space-y-8"}>
                        <p className={"font-bold"}>Bùi Thái Hoàng</p>
                        <p className={"font-bold"}>2002-04-02</p>
                        <p className={"font-bold"}>Male</p>
                        <p className={"font-bold"}>buithaihoang04gl@gmail.com</p>
                    </div>
                </div>


            </div>
            <div className={"flex flex-col pl-20 pr-20 pt-10 pb-10 space-y-8"} style={{background: "#FFFFFF1A"}}>
                <div className={"flex justify-between"}>
                    <p className={"font-bold text-orange-500"}>Login Account Information</p>
                    <p className={"font-bold text-orange-500"}>Reset Password</p>
                </div>

                <div className={"flex flex-row space-x-12"}>
                    <div className={"flex flex-col w-40 space-y-8"}>
                        <p>Phone number</p>
                        <p>Google</p>
                        <p>Ani4h ID</p>
                    </div>

                    <div className={"flex flex-col space-y-8"}>
                        <p className={"font-bold"}>0999988888</p>
                        <p className={"font-bold"}>Bùi Thái Hoàng</p>
                        <p className={"font-bold"}>Klkjdhjssay6</p>
                    </div>
                </div>
            </div>
        </div>
    )
}