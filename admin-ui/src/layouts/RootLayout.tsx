import {Outlet} from "react-router-dom";
import TopBar from "./TopBar.tsx";

export default function RootLayout() {
    return (
        <div>
            <TopBar/>
            <Outlet/>
        </div>
    )
}