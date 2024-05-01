import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

import { addBankCard } from '../api/BankCardApi';

const Container = styled.div`
  max-width: 400px;
  margin: 100px auto;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
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
  margin-bottom: 10px;
`;

const Button = styled.button`
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  margin-top: 20px;
  cursor: pointer;
  padding-top: 12px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #333;
  }
`;

const AddBankCardPage = () => {
  const [owner, setOwner] = useState('');
  const [number, setNumber] = useState('');
  const [expirationDate, setExpirationDate] = useState('');
  const [cvc, setCVC] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const userId = Cookies.get("userId");
    await addBankCard(owner, number, expirationDate, cvc, userId, navigate);
  };

  
  return (
    <Container>
      <Title>Add Bank Card</Title>
      <Form onSubmit={handleSubmit}>
      <Label htmlFor="cardOwner">Card Owner:</Label>
        <Input
          type="text"
          placeholder="Card Owner"
          value={owner}
          onChange={(e) => setOwner(e.target.value)}
          required
        />
        <Label htmlFor="cardNumber">Card Number:</Label>
        <Input
          type="text"
          placeholder="Card Number"
          value={number}
          onChange={(e) => setNumber(e.target.value)}
          required
        />
        <Label htmlFor="Expdate">Exp date:</Label>
        <Input
          type="text"
          placeholder="Expiration Date"
          value={expirationDate}
          onChange={(e) => setExpirationDate(e.target.value)}
          required
        />
        <Label htmlFor="cvc">Cvc code:</Label>
        <Input
          type="text"
          placeholder="CVC"
          value={cvc}
          onChange={(e) => setCVC(e.target.value)}
          required
        />
        <Button type="submit">Add Card</Button>
      </Form>
    </Container>
  );
};

export default AddBankCardPage;