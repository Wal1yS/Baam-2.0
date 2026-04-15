import type { UserCreateDTO, UserDTO, UserLoginDTO } from '../types/Authentification';

const API_BASE_URL = 'http://localhost:8000';

export async function loginUser(data: UserLoginDTO): Promise<UserDTO> {
    const response = await fetch(`${API_BASE_URL}/user/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Login failed');
    }

    return response.json();
}

export async function registerUser(data: UserCreateDTO): Promise<UserDTO> {
    const response = await fetch(`${API_BASE_URL}/user/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Registration failed');
    }

    return response.json();
}