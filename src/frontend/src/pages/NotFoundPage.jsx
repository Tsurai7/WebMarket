import React from 'react';
import styled from 'styled-components';

const Container = styled.div`
  text-align: center;
  margin-top: 90px;
`;

function NotFoundPage() {
  return (
    <Container>
      <h1 style={{ fontSize: '50px' }}>404 - Not Found</h1>
      <p style={{ fontSize: '22px' }}>The page you are looking for does not exist.</p>
    </Container>
  );
};

export default NotFoundPage;