import axios from 'axios';
import { createAsyncThunk } from "@reduxjs/toolkit";
import { fetchFileAPI, uploadFileAPI } from "./fileAPI";

// const API_URL = 'http://localhost:8080/FileCloud/file';


// Đây là một hàm async bình thường.
// Khi gọi fetchFile(), nó trả về một Promise chứa dữ liệu fileData.

// export const fetchFile = async () => {
//   const response = await axios.get(API_URL);
//     return response.data.fileData;
// };


// Đây là một Redux Toolkit createAsyncThunk action.
// createAsyncThunk là một công cụ của Redux giúp xử lý các tác vụ bất đồng bộ trong Redux.
// Khi gọi fetchFile(), nó không trả về trực tiếp Promise, mà thay vào đó, 
// Redux sẽ xử lý các trạng thái như pending, fulfilled, rejected.

// export const fetchFile = ("file/fetchFile", async (fileId, {rejectWithValue}) => {
//   try{
//   const response = await fetchFileAPI(fileId)
//   console.log(response)
//   return  response.data
  
//   }catch (err){
//     return rejectWithValue({
        
//       status: err.response?.status 
//     });
//   }
// });

export const uploadFile = createAsyncThunk("file/uploadFile", async (data, { rejectWithValue }) => {
  try {
    const response = await uploadFileAPI(data);
    return response.data;
  } catch (err) {
    return rejectWithValue({
      status: err.response?.status,
    });
  }
});