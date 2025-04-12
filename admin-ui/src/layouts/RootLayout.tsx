import {Outlet} from "react-router-dom";
import TopBar from "./TopBar.tsx";
import Drawer from "./Drawer.tsx";

export default function RootLayout() {
    return (
        <div>
            <TopBar/>
            <Drawer/>
            <Outlet/>
        </div>
    )
}