'use client'
import Link from "next/link";
import Image from "next/image";
import React, { FormEvent, useEffect, useState} from "react";
import {toast, ToastContainer} from "react-toastify";
export default function TopBar(className: { className?: string }) {
    const [isLogin, setIsLogin] = useState<boolean>(false)
    useEffect(() => {
        fetch("/api/me", {
            method: "GET"
        }).then(r => {
            if (r.status == 200) {
                setIsLogin(true)
            }
        })
    }, [])
    const onLogout = async (e: React.MouseEvent<HTMLAnchorElement> ) => {
        e.preventDefault();
        fetch("/api/logout").then(
            () =>{
                setIsLogin(false)
            }
        )
    }
    const onLogin = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        fetch("/api/login", {
            method: "POST",
            body: JSON.stringify({
                email: formData.get("email"),
                password: formData.get("password"),
            }),
        }).then(
            r => {
                if (r.status >= 500) {
                    toast.error("Error")
                } else if (r.status >= 400) {
                    toast.error("Invalid credentials")
                } else {
                    toast.success("Login success")
                    setIsLogin(true)
                }
            }
        )
    }
    return (
        <>
            <ToastContainer/>
            {/*Login Modal*/}
            <dialog id="login_modal" className="modal">
                <div className="modal-box rounded-md p-10 space-y-4">
                    <form method="dialog">
                        <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
                    </form>
                    <form action="/api/login" method={"POST"} onSubmit={onLogin}>
                        <div className={"space-y-4"}>
                            <input name={"email"} type="email" className={"input w-full rounded-md"}
                                   placeholder={"Enter your email"}/>
                            <input name={"password"} type="password" className={"input w-full rounded-md"}
                                   placeholder={"Enter your password"}/>
                            <button type={"submit"} className={"btn btn-primary w-full rounded-md"}>Login</button>
                        </div>
                    </form>
                    <p className={"text-center"}>Do you have an account?
                        <span onClick={() => {
                            (document.getElementById('login_modal') as HTMLDialogElement)?.close();
                            (document.getElementById('sign_up_modal') as HTMLDialogElement)?.showModal()
                        }} className={"text-primary cursor-pointer"}> Register now</span>
                    </p>
                    <div className="divider opacity-50">or log in by</div>
                    <div className={"flex justify-center space-x-10"}>
                        <Image className={"cursor-pointer"} src={"/images/icons/facebook-logo.svg"}
                               alt={"login with facebook"} width={40} height={40}></Image>
                        <Image className={"cursor-pointer "} src={"/images/icons/google-logo.svg"}
                               alt={"login with goole"} width={40} height={40}></Image>
                    </div>
                </div>
            </dialog>
            {/*Sign up modal*/}
            <dialog id="sign_up_modal" className="modal">
                <div className="modal-box rounded-md p-10 space-y-4">
                    <form method="dialog">
                        <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
                    </form>
                    <form action="/api/sign-up" method={"POST"}>
                        <div className={"space-y-4"}>
                            <input type="email" className={"input w-full rounded-md"} placeholder={"Enter your email"}/>
                            <button type={"submit"} className={"btn btn-primary w-full rounded-md"}>Send code</button>
                        </div>
                    </form>
                    <p onClick={() => {
                        (document.getElementById('sign_up_modal') as HTMLDialogElement)?.close();
                        (document.getElementById('login_modal') as HTMLDialogElement)?.showModal();
                    }
                    }
                       className={"text-center text-primary cursor-pointer"}>Already have an account?
                    </p>
                    <div className="divider opacity-50">or sign up with</div>
                    <div className={"flex justify-center space-x-10"}>
                        <Image className={"cursor-pointer"} src={"/images/icons/facebook-logo.svg"}
                               alt={"login with facebook"} width={40} height={40}></Image>
                        <Image className={"cursor-pointer"} src={"/images/icons/google-logo.svg"}
                               alt={"login with goole"} width={40} height={40}></Image>
                    </div>
                </div>
            </dialog>

            <div className={`navbar bg-base-red shadow-sm ${className} space-x-4`}>
                <div className="flex-none">
                    <Link href={"/"} className="btn btn-ghost text-xl">Ani4h</Link>
                </div>
                <div className="flex-1">
                    <ul className={"flex space-x-4"}>
                        <li><NavButton name={"Ranking"} href={"/"}/></li>
                        <li><NavButton name={"Genres"} href={"/"}/></li>
                        <li><NavButton name={"Library"} href={"/"}/></li>
                        <li><NavButton name={"Schedules"} href={"/"}/></li>
                    </ul>
                </div>
                <label className="input">
                    <input type="search" required placeholder="Search"/>
                    <svg className="h-[1em] opacity-50" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                        <g strokeLinejoin="round" strokeLinecap="round" strokeWidth="2.5" fill="none"
                           stroke="currentColor">
                            <circle cx="11" cy="11" r="8"></circle>
                            <path d="m21 21-4.3-4.3"></path>
                        </g>
                    </svg>
                </label>
                {/*profile avatar*/}
                {
                    isLogin ?
                        <div className="flex">
                            <div className="dropdown dropdown-end">
                                <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                                    <div className="w-10 rounded-full">
                                        <img
                                            alt="Tailwind CSS Navbar component"
                                            src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"/>
                                    </div>
                                </div>
                                <ul
                                    tabIndex={0}
                                    className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow">
                                    <li>
                                        <a className="justify-between">
                                            Profile
                                            <span className="badge">New</span>
                                        </a>
                                    </li>
                                    <li><a>Settings</a></li>
                                    <li><a onClick={onLogout}>Logout</a></li>
                                </ul>
                            </div>
                        </div> :
                        <div className={"space-x-4"}>
                            <div className={"btn btn-outline btn-primary "}
                                 onClick={() => (document.getElementById('login_modal') as HTMLDialogElement)?.showModal()}>Login
                            </div>
                            <div className={"btn btn-outline btn-primary "}
                                 onClick={() => (document.getElementById('sign_up_modal') as HTMLDialogElement)?.showModal()}>Signup
                            </div>
                        </div>
                }
                <div className={"btn btn-primary "}>Download</div>
            </div>
        </>
    )
}


function NavButton({name, href}: { name: string, href: string }) {
    return <Link className={"hover:text-primary"} href={href}>
        {name}
    </Link>
}