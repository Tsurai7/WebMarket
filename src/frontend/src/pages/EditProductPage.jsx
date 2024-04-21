import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';

const Container = styled.div`
  max-width: 600px;
  margin: 36px auto 0;
  padding: 20px;
`;

const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const InputWrapper = styled.div`
  margin-bottom: 20px;
`;

const Label = styled.label`
  font-weight: bold;
`;

const Input = styled.input`
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const TextArea = styled.textarea`
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const Button = styled.button`
  padding: 10px 20px;
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
`;

function EditProductPage() {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('');
    const [image, setImage] = useState('');
    const [brand, setBrand] = useState('');
    const navigate = useNavigate();
    const { productId } = useParams();
  
    useEffect(() => {
        const fetchProductInfo = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/products/getById?id=${productId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch product');
                }
                const productInfo = await response.json();

                console.log(productInfo);
                setTitle(productInfo.title);
                setDescription(productInfo.description);
                setCategory(productInfo.category);
                setImage(productInfo.image);
                setBrand(productInfo.brand);
            } catch (error) {
                console.error('Error fetching product:', error);
            }
        };

        fetchProductInfo();
    }, [productId]);
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const response = await fetch(`http://localhost:8080/api/products/update?id=${productId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ title, description, category, image, brand }),
        });
        if (response.ok) {
  
          console.log('Product updated successfully!');
  
          navigate('/myproducts');
        } else {
          console.error('Failed to update product');
        }
      } catch (error) {
        console.error('Error updating product:', error);
      }
    };

  return (
    <Container>
      <Title>Edit Product</Title>
      <Form onSubmit={handleSubmit}>
        <InputWrapper>
          <Label htmlFor="title">Title:</Label>
          <Input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </InputWrapper>
        <InputWrapper>
          <Label htmlFor="description">Description:</Label>
          <TextArea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </InputWrapper>
        <InputWrapper>
          <Label htmlFor="category">Category:</Label>
          <Input
            type="text"
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          />
        </InputWrapper>
        <InputWrapper>
          <Label htmlFor="image">Image URL:</Label>
          <Input
            type="text"
            id="image"
            value={image}
            onChange={(e) => setImage(e.target.value)}
          />
        </InputWrapper>
        <InputWrapper>
          <Label htmlFor="brand">Brand:</Label>
          <Input
            type="text"
            id="brand"
            value={brand}
            onChange={(e) => setBrand(e.target.value)}
          />
        </InputWrapper>
        <Button type="submit">Save Changes</Button>
      </Form>
    </Container>
  );
};

export default EditProductPage;
