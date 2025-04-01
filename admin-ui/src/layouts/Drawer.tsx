export default function Drawer() {
    return (
        <div className="drawer lg:drawer-open">
            <input id="my-drawer-2" type="checkbox" className="drawer-toggle"/>
            <div className="drawer-side">
                <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                <ul className="menu  text-base-content min-h-full w-52 p-4">
                    <li><a>Users</a></li>
                    <li><a>Film</a></li>
                </ul>
            </div>
        </div>
    )
}