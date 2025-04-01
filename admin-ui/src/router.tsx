import {Route, Routes} from "react-router-dom";
import Login from "./pages/login/Login.tsx";
import RootLayout from "./layouts/RootLayout.tsx";
import Dashboard from "./pages/dashboard/Dashboard.tsx";

export default function Router() {
    return (
        <Routes>
            <Route path={"/login"} element={<Login/>}></Route>
            <Route path={"/"} element={<RootLayout/>}>
                <Route index element={<Dashboard/>} />
            </Route>
        </Routes>
    )
}