import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const fetchFileAPI = (fileId) => {
  return axiosInstance.get('/file' + `?FileID=${fileId}`)
}

