import { NavLink } from 'react-router-dom';
import {
    HiHome,
    HiLocationMarker,
    HiCalendar,
    HiUser,
    HiCog,
} from 'react-icons/hi';

type DrawerMenuProps = {
    isOpen: boolean;
    onClose: () => void;
};

export default function DrawerMenu({ isOpen, onClose }: DrawerMenuProps) {
    return (
        <>
            <div
                className={isOpen ? 'drawer-overlay open' : 'drawer-overlay'}
                onClick={onClose}
            />

            <aside className={isOpen ? 'drawer-menu open' : 'drawer-menu'}>
                <div className="drawer-logo-box">
                    <img src="/baam-logo.png" alt="baam" className="drawer-logo-img" />
                </div>

                <nav className="drawer-nav">
                    <NavLink
                        to="/dashboard"
                        className={({ isActive }) =>
                            isActive ? 'drawer-link active' : 'drawer-link'
                        }
                        onClick={onClose}
                    >
                        <HiHome className="drawer-icon" />
                        <span>Dashboard</span>
                    </NavLink>

                    <NavLink
                        to="/attendance"
                        className={({ isActive }) =>
                            isActive ? 'drawer-link active' : 'drawer-link'
                        }
                        onClick={onClose}
                    >
                        <HiLocationMarker className="drawer-icon" />
                        <span>Attendance</span>
                    </NavLink>

                    <NavLink
                        to="/sessions"
                        className={({ isActive }) =>
                            isActive ? 'drawer-link active' : 'drawer-link'
                        }
                        onClick={onClose}
                    >
                        <HiCalendar className="drawer-icon" />
                        <span>Sessions</span>
                    </NavLink>

                    <NavLink
                        to="/profile"
                        className={({ isActive }) =>
                            isActive ? 'drawer-link active' : 'drawer-link'
                        }
                        onClick={onClose}
                    >
                        <HiUser className="drawer-icon" />
                        <span>Profile</span>
                    </NavLink>

                    <NavLink
                        to="/settings"
                        className={({ isActive }) =>
                            isActive ? 'drawer-link active' : 'drawer-link'
                        }
                        onClick={onClose}
                    >
                        <HiCog className="drawer-icon" />
                        <span>Settings</span>
                    </NavLink>
                </nav>
            </aside>
        </>
    );
}