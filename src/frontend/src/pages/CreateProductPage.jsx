import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Cookies from 'js-cookie';


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

function MyProducts() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [category, setCategory] = useState('');
  const [image, setImage] = useState('');
  const [brand, setBrand] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/products/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title, description, category, image, brand }),
      });
      if (response.ok) {

        console.log('Product created successfully!');

        const data = await response.json();
        console.log("Id: ", data.id);
        addNumberToCookieArray(data.id);

        navigate('/myproducts');

        setTitle('');
        setDescription('');
        setCategory('');
        setImage('');
        setBrand('');
      } else {
        console.error('Failed to create product');
      }
    } catch (error) {
      console.error('Error creating product:', error);
    }
  };

  const addNumberToCookieArray = (numberToAdd) => {
    const cookieArrayString = Cookies.get('myProducts');
  
    const cookieArray = cookieArrayString ? JSON.parse(cookieArrayString) : [];

    cookieArray.push(numberToAdd);
  
    const updatedCookieArrayString = JSON.stringify(cookieArray);
  
    Cookies.set('myProducts', updatedCookieArrayString);
  };

  return (
    <Container>
      <Title>Create Product</Title>
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
        <Button type="submit">Create</Button>
      </Form>
    </Container>
  );
}

export default MyProducts;
