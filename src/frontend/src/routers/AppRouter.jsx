import CatalogPage from '../pages/CatalogPage.jsx';
import Authorize from '../pages/SignUpPage.jsx';
import UserProfile from '../pages/UserProfilePage.jsx';
import MyProducts from '../pages/MyProductsPage.jsx';
import SignUpPage from '../pages/SignUpPage.jsx';
import SignIn from '../pages/SignInPage.jsx';
import AddBankCard from '../pages/AddBankCardPage.jsx';
import CreateProduct from '../pages/CreateProductPage.jsx';
import EditProductPage from '../pages/EditProductPage.jsx';
import NotFoundPage from '../pages/NotFoundPage.jsx';

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function AppRouter() {
    return(
        <Router>
        <Routes>
          <Route path="/catalog" element={<CatalogPage />} />
          <Route path="/register" element={<Authorize />} />
          <Route path="/myproducts" element={<MyProducts />} />
          <Route path="/create-product" element={<CreateProduct />} />
          <Route path="/signUp" element={<SignUpPage />} />
          <Route path="/signIn" element={<SignIn />} />
          <Route path="/profile" element={<UserProfile />} />
          <Route path="/addbankcard" element={<AddBankCard />} />
          <Route path="/edit-product/:productId" element={<EditProductPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
        </Router>
    )
}

export default AppRouter;