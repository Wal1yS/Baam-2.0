import { useNavigate } from 'react-router-dom';
import AppShell from '../components/AppShell';

export default function Profile() {
    const navigate = useNavigate();

    const user = {
        name: 'Enje Example',
        email: 'e.shaikhutdinova@innopolis.university',
        avatarUrl: null,
    };

    function handleLogout() {
        navigate('/login');
    }

    return (
        <AppShell title="Profile">
            <div className="profile-card">

                <div className="profile-avatar">
                    {user.avatarUrl ? (
                        <img src={user.avatarUrl} alt="avatar" className="profile-avatar-img" />
                    ) : (
                        <div className="profile-avatar-placeholder">
                            {user.email
                                .split('@')[0]
                                .split('.')
                                .map((part: string) => part.charAt(0).toUpperCase())
                                .join('')}
                        </div>
                    )}
                </div>

                <div className="profile-info">
                    <p className="profile-name">{user.name}</p>
                    <p className="profile-email">{user.email}</p>
                </div>

                <button className="profile-logout-btn" onClick={handleLogout}>
                    Logout
                </button>

            </div>
        </AppShell>
    );
}