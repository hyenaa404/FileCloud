import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const fetchFileAPI = (fileId) => {
  return axiosInstance.get('/file' + `?FileID=${fileId}`)
}

export const uploadFileAPI = (data) => {
  const formData = new FormData();
  formData.append("file", data.file);
  formData.append("folderId", data.folderID);

  return axiosInstance.post("/upload", formData, {
    withCredentials: true,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
