/*import { type FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { loginUser } from '../api/Authentification';

export default function LoginPage() {
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setErrorMessage('');
        setLoading(true);

        try {
            await loginUser({ email, password });
            navigate('/dashboard');
        } catch (error) {
            console.error(error);
            setErrorMessage('Login failed');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="auth-screen">
            <div className="auth-inner">
                <img
                    src="/iu-logo.png"
                    alt="Innopolis University"
                    className="iu-logo"
                />

                <h1 className="auth-heading">Login</h1>

                <form className="auth-form" onSubmit={handleSubmit}>
                    <input
                        className="auth-input"
                        type="email"
                        placeholder="proverka@example.com"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />

                    <input
                        className="auth-input"
                        type="password"
                        placeholder="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />

                    {errorMessage ? <p className="auth-error">{errorMessage}</p> : null}

                    <button className="auth-submit" type="submit" disabled={loading}>
                        {loading ? 'Loading...' : 'Login'}
                    </button>
                </form>

                <div className="auth-secondary-link">
                    <Link to="/register">Register</Link>
                </div>

                <div className="auth-bottom-link">
                    <span>Change password</span>
                </div>
            </div>
        </div>
    );
}

*/
import { type FormEvent, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        navigate('/dashboard');
    }

    return (
        <div className="auth-screen">
            <div className="auth-inner">
                <img
                    src="/iu-logo.png"
                    alt="Innopolis University"
                    className="iu-logo"
                />

                <h1 className="auth-heading">Login</h1>

                <form className="auth-form" onSubmit={handleSubmit}>
                    <input
                        className="auth-input"
                        type="email"
                        placeholder="proverka@example.com"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />

                    <input
                        className="auth-input"
                        type="password"
                        placeholder="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />

                    <button className="auth-submit" type="submit">
                        Login
                    </button>
                </form>

                <div className="auth-secondary-link">
                    <Link to="/register">Register</Link>
                </div>

                <div className="auth-bottom-link">
                    <span>Change password</span>
                </div>
            </div>
        </div>
    );
}