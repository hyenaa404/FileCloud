import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const fetchFolderAPI = (folderId) => {
  return axiosInstance.get('/folder' + `?FolderID=${folderId}`, {
    withCredentials: true
  })
}


