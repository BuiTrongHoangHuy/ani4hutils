import {Link, Outlet} from "react-router-dom";
import TopBar from "./TopBar.tsx";

export default function Drawer() {
    return (
        <div className="drawer lg:drawer-open">
            <input id="my-drawer-2" type="checkbox" className="drawer-toggle"/>
            <div className="drawer-content p-4">
                <TopBar/>
                <Outlet/>
            </div>
            <div className="drawer-side">
                <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                <ul className="menu text-base-content min-h-full w-52 p-4 gap-5">
                    <div className="flex-none">
                        <a href={"/"} className="btn btn-ghost text-xl">Ani4h</a>
                    </div>
                    <li><Link to="/film">Films</Link></li>
                </ul>
            </div>
        </div>
    )
}