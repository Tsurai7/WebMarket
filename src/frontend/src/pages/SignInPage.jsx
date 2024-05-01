import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import styled from 'styled-components';
import { signIn } from '../api/UserApi.jsx';

const Container = styled.div`
  max-width: 400px;
  margin: 80px auto;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const TitleContainer = styled.div`
  display: flex;
  justify-content: center; /* Распределяем пространство между элементами */
  margin-bottom: 20px;
`;

const Title = styled.h2`
  font-size: 24px;
  font-weight: 700;
  color: #333;
  margin-right: 20px;
  margin-left: 20px;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
  display: flex; /* Используем flexbox */
  flex-direction: column; /* Устанавливаем направление колонкой */
`;

const Label = styled.label`
  font-weight: 600;
  margin-bottom: 2px;
  color: #333;
  font-family: Open Sans , sans-serif;
`;

const Input = styled.input`
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 16px;
`;

const Button = styled.button`
  padding: 12px;
  margin-right: 40px;
  margin-left: 40px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-size: 16px;
  &:hover {
    background-color: #0056b3;
  }
`;


function SignInPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = await signIn(email, password);

    console.log('User logged in successfully:', data);
    
    Cookies.set('username', data.name);
    Cookies.set('password', data.password);
    Cookies.set('profileImage', data.image);
    Cookies.set('userId', data.id);
    
    navigate('/catalog');
    window.location.reload();
  };

  return (
    <Container>
      <TitleContainer>
      <Link to="/signin">
        <Title>Sign In</Title>
      </Link>
      <Link to="/signup">
        <Title>Sign Up</Title>
      </Link>
    </TitleContainer>
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label htmlFor="email">Email:</Label>
          <Input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
            required
          />
        </FormGroup>
        <FormGroup>
          <Label htmlFor="password">Password:</Label>
          <Input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Enter your password"
            required
          />
        </FormGroup>
        <Button type="submit">Sign In</Button>
      </Form>
    </Container>
  );
}

export default SignInPage;
