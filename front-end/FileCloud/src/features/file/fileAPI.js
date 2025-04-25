import axios from 'axios';
import axiosInstance from "../../services/axiosInstance";


export const fetchFileAPI = async (fileId) => {
  // return axiosInstance.get('/file' + `?FileID=${fileId}`, {
  //   responseType: 'blob' 
  // })
  return await axiosInstance.get('/file?FileID=' + fileId, {
    responseType: 'blob'
  })
}

// fetch('http://localhost:8080/api/file?id=123')
//   .then(res => res.blob())
//   .then(blob => {
//     const url = URL.createObjectURL(blob);
//     window.open(url); 
//   });


export const uploadFileAPI = (data) => {
  const formData = new FormData();
  formData.append("file", data.file);
  formData.append("folderID", data.folderID);

  return axiosInstance.post("/file", formData, {
    withCredentials: true,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const deleteFileAPI = (data) => {
  // console.log(data.fileID)
  return axiosInstance.delete('/file', {
    data: { fileID: data.fileID }
    ,
    withCredentials: true
  })
}

export const updateFileAPI = (data) => {
  return axiosInstance.put('/file', {

    fileID: data.fileID,
    fileName: data.newName
  }
    ,
    {
      withCredentials: true
      ,
      headers: {
        "Content-Type": "application/json",
      },
    })
}


