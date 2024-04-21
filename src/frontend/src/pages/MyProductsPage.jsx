import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Cookies from 'js-cookie';
import { removeProduct, getAllProducts } from '../api/ProductApi';

const Container = styled.div`
  margin: 25px auto 0;
  padding: 80px;
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

const ProductInfo = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex: 1;
`;


const Button = styled(Link)`
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  text-decoration: none;
  cursor: pointer;
  margin-right: 4px;
  margin-left: 4px;
`;


const MyProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [productIds, setProductIds] = useState([]);

const handleRemoveProduct = async (productId) => {
  await removeProduct(productId)

  const updatedProductIds = productIds.filter(id => id !== productId);
  Cookies.set('myProducts', JSON.stringify(updatedProductIds));

  const updatedProducts = products.filter(product => product.id !== productId);
};
  

  useEffect(() => {
    const fetchProductInfo = async (productId) => {
      try {
        const response = await fetch(`http://localhost:8080/api/products/getById?id=${productId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch product');
        }
        const productInfo = await response.json();
        console.log(productInfo);

        return productInfo;
        
      } catch (error) {
        console.error(`Error fetching product ${productId}:`, error);
        return null;
      }
    };

    const fetchProductsFromCookie = async () => {
      const productIds = Cookies.get('myProducts') ? JSON.parse(Cookies.get('myProducts')) : [];
      const productPromises = productIds.map(productId => fetchProductInfo(productId));

      const productsData = await Promise.all(productPromises);

      const validProducts = productsData.filter(product => product !== null);

      console.log(validProducts)
      setProducts(validProducts);
    };

    fetchProductsFromCookie();
  }, []);

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
              <div>
                <Button to={`/edit-product/${product.id}`}>Edit</Button>
                <Button onClick={() => handleRemoveProduct(product.id)}>Delete</Button>
              </div>
            </ProductInfo>
          </ProductItem>
        ))}
      </ProductList>
      <Button to="/create-product">Create Product</Button>
    </Container>
  );
};

export default MyProductsPage;