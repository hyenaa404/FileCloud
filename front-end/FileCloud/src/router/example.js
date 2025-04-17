import React from "react";
import { Routes, Route } from "react-router-dom";
import MainLayout from "../layouts/LandingLayout";
import ProtectedLayout from "../layouts/ProtectedLayout";
import LandingPage from "../pages/LandingPage";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import HomePage from "../pages/HomePage";
import NotFoundPage from "../pages/NotFoundPage";

const AppRouter = () => {
  return (
    <Routes>
      {/* Public Layout - không cần đăng nhập */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* Protected Layout - cần đăng nhập */}
      <Route element={<ProtectedLayout />}>
        <Route element={<MainLayout />}>
          <Route path="/home" element={<HomePage />} />
          <Route path="/setting" element={<div>Settings</div>} />
        </Route>
      </Route>

      {/* 404 */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};

export default AppRouter;
