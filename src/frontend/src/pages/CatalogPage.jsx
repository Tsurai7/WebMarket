import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Cookies from 'js-cookie';

const Container = styled.div`
  margin: 25px auto 0;
  padding: 20px;
`;

const ProductList = styled.ul`
  list-style: none;
  padding: 0;
`;

const ProductItem = styled.li`
  display: flex;
  align-items: center;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 8px;
  margin-bottom: 8px;
`;

const ProductTitle = styled.h1`
  margin-bottom: 10px;
  font-size: 1.2rem;
`;

const ProductDescription = styled.p`
  margin-bottom: 10px;
`;

const ProductImage = styled.img`
  width: 200px;
  height: auto;
  margin-right: 20px;
`;

const AddToCartButton = styled.button`
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #333;
  }
`;

const RemoveFromCartButton = styled(AddToCartButton)`
  background-color: #ff0000;
`;

const CartQuantity = styled.span`
  margin: 0 10px;
`;

const ProductInfo = styled.div`
  flex: 1;
`;

function CatalogPage() {
  const [products, setProducts] = useState([]);
  const [cartItems, setCartItems] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cartUpdated, setCartUpdated] = useState(false);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/products/getAll');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setProducts(data);
      } catch (error) {
        setError(error);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [cartUpdated]);


  const addToCart = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/addProduct?userId=${Cookies.get('userId')}&productId=${productId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        }
      });
  
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      setCartItems(prevState => ({
        ...prevState,
        [productId]: (prevState[productId] || 0) + 1
      }));
    } catch (error) {
      setError(error);
    }
  };


  const removeFromCart = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/removeProduct?userId=${Cookies.get('userId')}&productId=${productId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        }
      });
  
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
  
      setCartItems(prevState => ({
        ...prevState,
        [productId]: (prevState[productId] || 0) - 1
      }));
    } catch (error) {
      setError(error);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <Container>
      <ProductList>
        {products.map((product) => (
          <ProductItem key={product.id}>
            <ProductImage src={product.image} alt={product.title} />
            <ProductInfo>
              <ProductTitle>{product.title}</ProductTitle>
              <p>Id: {product.id}</p>
              <ProductDescription>{product.description}</ProductDescription>
              <p>Category: {product.category}</p>
              <p>Price: ${product.price}</p>
            </ProductInfo>
            {cartItems[product.id] ? (
              <div>
                <AddToCartButton onClick={() => addToCart(product.id)}>+</AddToCartButton>
                <CartQuantity>{cartItems[product.id]}</CartQuantity>
                <RemoveFromCartButton onClick={() => removeFromCart(product.id)}>-</RemoveFromCartButton>
              </div>
            ) : (
              <AddToCartButton onClick={() => addToCart(product.id)}>Add to Cart</AddToCartButton>
            )}
          </ProductItem>
        ))}
      </ProductList>
    </Container>
  );
}

export default CatalogPage;
