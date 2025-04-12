import {login} from "./api.ts";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

export default function Login() {
    const navigate = useNavigate()
    return (
        <div className="w-screen h-screen flex items-center justify-center">
            <form   onSubmit={(e) =>{
                e.preventDefault()
                const email = (document.getElementById("email") as HTMLInputElement)?.value;
                const password = (document.getElementById("password") as HTMLInputElement)?.value;
                login(
                    {email : email!,password: password!}
                ).then(
                    r => {
                        console.log(r)
                        localStorage.setItem("token",r.data.accessToken)
                        localStorage.setItem("refreshToken",r.data.refreshToken)
                        toast.success("Login success")
                        navigate("/");
                    }
                )
            }}>
                <div className={"space-y-4 bg-gray-400 p-10 rounded-xl"}>
                    <p className={"text-center uppercase font-bold text-2xl"}>Ani4h admin</p>
                    <div className={"space-y-3"}>
                        <input id={"email"} type="text" placeholder="email" className="input w-full"/>
                        <input id={"password"} type="password" placeholder="password" className="input w-full"/>
                    </div>
                    <button type={"submit"} className={"btn btn-primary w-full"}>
                        Login
                    </button>
                </div>
            </form>
        </div>
    )
}