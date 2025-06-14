
export const loginAPI = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            
            // Attempt to parse error message from backend if available
            const errorData = await response.json();
            const errorMessage = errorData?.errorMessage || `Login failed with status: ${response.status}`;
            throw new Error(errorMessage);
        }

        // Assuming the backend returns some user data or a token upon successful login
        const data = await response.json();
        return { success: true, token: data.token }; // Adjust based on your API response
    } catch (error) {
        console.error('Login API error:', error);
        throw error; // Re-throw the error to be caught by the component
    }
};
