import CatalogPage from '../pages/CatalogPage.jsx';
import UserProfile from '../pages/UserProfilePage.jsx';
import MyProducts from '../pages/MyProductsPage.jsx';
import SignUpPage from '../pages/SignUpPage.jsx';
import SignInPage from '../pages/SignInPage.jsx';
import AddBankCard from '../pages/AddBankCardPage.jsx';
import CreateProduct from '../pages/CreateProductPage.jsx';
import EditProductPage from '../pages/EditProductPage.jsx';
import NotFoundPage from '../pages/NotFoundPage.jsx';

import Cookies from 'js-cookie';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';


function AppRouter() {
    const isLoggedIn = Cookies.get('userId');

    return (
        <Router>
          <Routes>
            {isLoggedIn ? (
              <>
                <Route path="/catalog" element={<CatalogPage />} />
                <Route path="/myproducts" element={<MyProducts />} />
                <Route path="/create-product" element={<CreateProduct />} />
                <Route path="/profile" element={<UserProfile />} />
                <Route path="/addbankcard" element={<AddBankCard />} />
                <Route path="/edit-product/:productId" element={<EditProductPage />} />
              </>
            ) : (
              <>
                <Route path="/catalog" element={<NotFoundPage />} />
                <Route path="/myproducts" element={<NotFoundPage />} />
                <Route path="/create-product" element={<NotFoundPage />} />
                <Route path="/profile" element={<NotFoundPage />} />
                <Route path="/addbankcard" element={<NotFoundPage />} />
                <Route path="/edit-product/:productId" element={<NotFoundPage />} />
              </>
            )}
            <Route path="/signUp" element={<SignUpPage />} />
            <Route path="/signIn" element={<SignInPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </Router>
      );
}

export default AppRouter;