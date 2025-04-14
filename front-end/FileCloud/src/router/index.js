import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import Home from '../pages/Home';
import Setting from '../pages/Setting';
import LoginForm from '../features/authenticate/components/LoginForm';

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainLayout />,
    children: [
      { index: true, element: <Home /> },           // /home
      { path: 'setting', element: <Setting /> },    // 
      {path: 'login', element: <LoginForm/>}
    ]
  }
]);

const AppRouter = () => <RouterProvider router={router} />;
export default AppRouter;