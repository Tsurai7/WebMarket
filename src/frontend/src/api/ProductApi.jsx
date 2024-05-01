const BASE_URL = 'http://localhost:8080/api/products';

export const getById = async (productId) => {
    const response = await fetch(`${BASE_URL}/getById?id=${productId}`);

    if (!response.ok) {
        throw new Error('Failed to fetch product');
    }
    const productInfo = await response.json();

    console.log(productInfo);

    return productInfo;
};
  

export const removeProduct = async (productId) => {
    const response = await fetch(`${BASE_URL}/delete?id=${productId}`, {
    method: 'DELETE',
    headers: {
        'Content-Type': 'application/json',
    }

    });

    if (!response.ok) {
    throw new Error('Failed to remove product');
        }
};

