import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

const Container = styled.div`
  padding: 20px;
  margin: 30px auto;
`;

const UserDataContainer = styled.div`
  margin-bottom: 40px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  display: flex;
  align-items: center;
`;

const UserInfo = styled.div`
  flex: 1;
`;

const ProductTitle = styled.h1`
  margin-bottom: 10px;
  font-size: 1.2rem;
`;


const ProductInfo = styled.div`
  flex: 1;
`;

const UserImage = styled.img`
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin-right: 20px;
`;

const UserHeading = styled.h3`
  margin-bottom: 10px;
`;

const UserData = styled.p`
  margin: 5px 0;
`;

const ExpandableList = styled.ul`
  list-style: none;
  padding: 0;
`;

const ExpandableListItem = styled.li`
  display: flex;
  align-items: center;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 8px;
  margin-bottom: 8px;
`;

const ProductImage = styled.img`
  width: 200px;
  height: auto;
  margin-right: 20px;
`;

const ProductQuantity = styled.span`
  margin: 0 10px;
  font-weight: bold;
`;

const Button = styled.button`
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  margin-right: 4px;
  margin-left: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #333;
  }
`;


const UserProfilePage = () => {
  const [userData, setUserData] = useState(null);
  const [expandedProducts, setExpandedProducts] = useState(false);
  const [expandedBankCards, setExpandedBankCards] = useState(false);
  const navigate = useNavigate(); 
  const userId = Cookies.get('userId');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/users/getById?id=${userId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch user data');
        }
        const userData = await response.json();
        setUserData(userData);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, [userId]);

  const toggleProducts = () => {
    setExpandedProducts(!expandedProducts);
  };

  const toggleBankCards = () => {
    setExpandedBankCards(!expandedBankCards);
  };

  const groupProductsById = (products) => {
    const groupedProducts = {};
    products.forEach(product => {
      if (!groupedProducts[product.id]) {
        groupedProducts[product.id] = { ...product, quantity: 1 };
      } else {
        groupedProducts[product.id].quantity++;
      }
    });
    return Object.values(groupedProducts);
  };

  const addBankCard = async () => {
    navigate('/addBankCard');
  };

  const fetchUserData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/getById?id=${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch user data');
      }
      const userData = await response.json();
      setUserData(userData);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const handleAddProduct = async (productId) => {
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
    
      fetchUserData();
    } catch (error) {
      console.error('Error adding product:', error);
    }
  };

  const handleRemoveProduct = async (productId) => {
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
      
      fetchUserData();
    } catch (error) {
      console.error('Error removing product:', error);
    }
  };

  const handleRemoveCard = async (cardId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/cards/remove?userId=${Cookies.get('userId')}&cardId=${cardId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok ' + cardId);
      }

      fetchUserData();
    } catch (error) {
      console.error('Error removing card:', error);
    }
  };



  const logout = () => {
    Cookies.remove('userId');
    Cookies.remove('username');
    Cookies.remove('password');
    Cookies.remove('profileImage');
    navigate('/signup');
    window.location.reload();
  };


  const renderExpandableListProducts = (data, titleKey, contentKeys, expanded, toggleHandler) => {
    return (
      <>
        <h3 onClick={toggleHandler}>{titleKey}</h3>
        {expanded && (
          <ExpandableList>
            {groupProductsById(data).map(item => (
              <ExpandableListItem key={item.id}>
                <ProductInfo>
                    <ProductTitle>{item[titleKey]}</ProductTitle>
                    <ProductImage src={item.image} alt={item.title} />
                    {contentKeys.map(key => (
                      <p key={key}>{key}: {item[key] }</p>
                    ))}
                    </ProductInfo>
                  <div>
                      <Button style={{ marginLeft: 'auto' }} onClick={() => handleAddProduct(item.id)}>+</Button>
                      <ProductQuantity>{item.quantity}</ProductQuantity>
                      <Button onClick={() => handleRemoveProduct(item.id)}>-</Button>
                  </div>
              </ExpandableListItem>
            ))}
          </ExpandableList>
        )}
      </>
    );
  };
  

  const renderExpandableListCards = (data, titleKey, contentKeys, expanded, toggleHandler) => {
    return (
      <>
        <h3 onClick={toggleHandler}>{titleKey}</h3>
        {expanded && (
          <ExpandableList>
            <Container>
            {groupProductsById(data).map(item => (
              <ExpandableListItem key={item.id}>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  <div style={{ flex: 1 }}>
                    <p>{item[titleKey]}</p>
                    {contentKeys.map(key => (
                      <p key={key}>{key}: {item[key]}</p>
                    ))}
                  </div>
                  <div>
                  <Button onClick={() => handleRemoveCard(item.id)}>Remove card</Button>
                  </div>
                </div>
              </ExpandableListItem>           
            ))}
            </Container>
          </ExpandableList>
        )}
      </>
    );
  };

  return (
    <Container>
      {userData ? (
        <div>
          <UserDataContainer>
            <UserImage src={Cookies.get('profileImage') !== undefined ? Cookies.get('profileImage') : 'https://sun9-25.userapi.com/impg/_7v95fIPpNcU0bUPS-A9tUwjrbrviv24lEwWbw/DPZxKLBFgbA.jpg?size=800x800&quality=96&sign=0544c0bc5c9852c5d4ccc1e7102428e2&c_uniq_tag=Qo9crsjqzU1ngG34RehSm8T_a6KqibgCsfbMsTmNMoY&type=album'}/>
            <UserInfo>
              <UserHeading>{userData.name}</UserHeading>
              <UserData>Id: {userData.id}</UserData>
              <UserData>Email: {userData.email}</UserData>
              <UserData>Password: {userData.password}</UserData>
              <Button style={{ 'background-color': '#333333', 'margin-left': '100px' }} onClick={logout}>Logout</Button>
            </UserInfo>
          </UserDataContainer>

          {renderExpandableListProducts(userData.products, 'Products', ['description', 'category'], expandedProducts, toggleProducts)}

          {renderExpandableListCards(userData.bankCards, 'Bank Cards', ['owner', 'number', 'expirationDate', 'cvc'], expandedBankCards, toggleBankCards)}

          <Button onClick={addBankCard}>Add Bank Card</Button>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </Container>
  );
}

export default UserProfilePage;
