import React from 'react';
import Cookies from 'js-cookie';
import styled from 'styled-components';

const NavbarContainer = styled.nav`
  padding: 10px 0;
  width: 100%;
  height: 54px;
  background-color: #333;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;
`;

const NavMenu = styled.ul`
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style: none;
  margin: 0 20px;
  padding: 0;
`;

const NavItem = styled.li`
  font-size: 28px;
  margin: 0;
  transition: background-color 0.5s, color 0.5s;
`;

const NavLink = styled.a`
  display: flex;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.3s ease;
  padding: 5px;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: #555; /* изменение цвета при наведении */
  }
  &.active {
    background-color: #555; /* изменение цвета активной ссылки */
  }
`;

const ProfileImage = styled.img`
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
`;

const Navbar = () => {
  return (
    <NavbarContainer>
      <NavMenu>
        <NavItem>
          <NavLink href="/catalog" className={window.location.pathname === '/catalog' ? 'active' : ''}>Catalog</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/myproducts" className={window.location.pathname === '/myproducts' ? 'active' : ''}>My Products</NavLink>
        </NavItem>
        <NavItem>
          {Cookies.get('username') && Cookies.get('password') ? (
            <NavLink href="/profile" className={window.location.pathname === '/profile' ? 'active' : ''}>
              <div className="user_profile">
                <ProfileImage src={Cookies.get('profileImage') != undefined ? Cookies.get('profileImage') : 'https://sun9-25.userapi.com/impg/_7v95fIPpNcU0bUPS-A9tUwjrbrviv24lEwWbw/DPZxKLBFgbA.jpg?size=800x800&quality=96&sign=0544c0bc5c9852c5d4ccc1e7102428e2&c_uniq_tag=Qo9crsjqzU1ngG34RehSm8T_a6KqibgCsfbMsTmNMoY&type=album'} alt="Profile" />
                <p className="user_profile_username">{Cookies.get('username')}</p>
              </div>
            </NavLink>
          ) : (
            <NavLink href="/signup" className={window.location.pathname === '/signUp' ? 'active' : ''}>Auth</NavLink>
          )}
        </NavItem>
      </NavMenu>
    </NavbarContainer>
  );
}

export default Navbar;
