import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

import { addBankCard } from '../api/UserApi';

const Container = styled.div`
  max-width: 400px;
  margin: 80px auto;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
`;

const Button = styled.button`
  padding: 12px;
  margin-top: 16px;
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