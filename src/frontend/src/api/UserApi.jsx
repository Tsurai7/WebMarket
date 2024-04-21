const BASE_URL = 'http://localhost:8080/api/users';


export const getUserById = async (id) => {
    const response = await fetch(`${BASE_URL}/getById?id=${id}`);
    const data = await response.json();
    return data;
}


export const signIn = async (email, password) => {
    
    const response = await fetch(`${BASE_URL}/signIn`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    });
    console.log(response)

    if (!response.ok) {
        throw new Error('Error signing in');
    }

    const data = await response.json();
    
    return data;
}


export const signUp = async (name, email, image, password) => {

    const response = await fetch(`${BASE_URL}/signUp`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email, image, password })
    });

    if (!response.ok) {
        throw new Error('Error signing in');
    }

    const data = await response.json();

    return data;
}


export const addToCart = async (userId, productId) => {
    const response = await fetch(`${BASE_URL}/addProduct?userId=${userId}&productId=${productId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const data = await response.json();
    return data;
}


export const removeFromCart = async (userId, productId) => {
    const response = await fetch(`${BASE_URL}/removeProduct?userId=${userId}&productId=${productId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const data = await response.json();
    return data;
}


export const addBankCard = async (userId, bankCard) => {
    const response = await fetch(`${BASE_URL}/addBankCard?userId=${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ bankCard })
    });
    const data = await response.json();
    return data;
}


export const removeBankCard = async (userId, bankCardId) => {
    const response = await fetch(`${BASE_URL}/removeBankCard?userId=${userId}&bankCardId=${bankCardId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const data = await response.json();
    return data;
}