import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const authenticateAPI = () => {
  return axiosInstance.get("/auth/status", {
    credentials: "include", 
  });
}

export const loginAPI = (data) => {
  return axiosInstance.post('/login',{
    email: data.email,
    password: data.password
  })
}

export const registerAPI = (data) => {
    return axiosInstance.post('/register', data)
}

export const logoutAPI = () => {
  return axiosInstance.get('/logout')
}

