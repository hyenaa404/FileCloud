import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const loginAPI = (data) => {
  return axiosInstance.post('/login',data)
}

export const registerAPI = (data) => {
    return axiosInstance.post('/register', data)
}

export const logoutAPI = () => {
  return axiosInstance.get('/logout')
}

