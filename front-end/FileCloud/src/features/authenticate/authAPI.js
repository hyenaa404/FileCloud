import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const authenticateAPI = () => {
  return axiosInstance.get("/auth/status", {
    withCredentials: true,
  });
}

export const loginAPI = (data) => {
  return axiosInstance.post('/login',{
    email: data.email,
    password: data.password
  }, {
    withCredentials: true
  })
}

// export const loginGoogleAPI = (data) => {
//   return axiosInstance.post('/login-google',{
//     email: data.email,
//     password: data.password
//   }, {
//     withCredentials: true
//   })
// }

export const registerAPI = (data) => {
    return axiosInstance.post('/register', data)
}

export const logoutAPI = () => {
  return axiosInstance.get('/logout', {
    withCredentials: true,
  })
}

