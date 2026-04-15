import { type ReactNode, useState } from 'react';
import DrawerMenu from './DrawerMenu';

type AppShellProps = {
    title: string;
    children: ReactNode;
};

export default function AppShell({ title, children }: AppShellProps) {
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    return (
        <div className="app-shell">
            <DrawerMenu isOpen={isMenuOpen} onClose={() => setIsMenuOpen(false)} />

            <header className="app-header">
                <button
                    className="logo-button"
                    type="button"
                    onClick={() => setIsMenuOpen(true)}
                >
                    <img src="/baam-logo.png" alt="Open menu" className="header-logo" />
                </button>
            </header>

            <main className="app-page">
                <h1 className="page-title">{title}</h1>
                <div className="page-empty-card">{children}</div>
            </main>
        </div>
    );
}