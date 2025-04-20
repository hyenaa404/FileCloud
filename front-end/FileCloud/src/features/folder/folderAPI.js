import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const fetchFolderAPI = (folderId) => {
  return axiosInstance.get('/folder' + `?FolderID=${folderId}`, {
    withCredentials: true
  })
}


export const uploadFileAPI = (folderId, file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("folderId", folderId);
  
    return axiosInstance.post("/upload", formData, {
      withCredentials: true,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };
  

