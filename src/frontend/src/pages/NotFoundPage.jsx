import React from 'react';
import styled from 'styled-components';

const Container = styled.div`
  text-align: center;
  margin-top: 50px;
`;

const NotFoundPage = () => {
  return (
    <Container>
      <h1>404 - Not Found</h1>
      <p>The page you are looking for does not exist.</p>
    </Container>
  );
};

export default NotFoundPage;