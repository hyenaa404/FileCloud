
import React from 'react';
import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { checkAuthStatus } from '../features/authenticate/authThunk';
import route from './landing-router';
import MainLayout from '../layouts/MainLayout';

const PrivateRoute = ({ children }) => {

    const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
    

    if (isAuthenticated === false) {
        return <Navigate to="/login" replace />;
    }
        return children;
    

};

export default PrivateRoute;
