import { createAsyncThunk } from "@reduxjs/toolkit";
import { fetchFolderAPI } from "./folderAPI";

export const fetchFolder = createAsyncThunk("folder/fetchFolder", async (folderId, {rejectWithValue}) => {
  try{
  const response = await fetchFolderAPI(folderId)
  console.log(response.data)
  return response.data
  }catch (err){
    return rejectWithValue({
        
      status: err.response?.status 
    });
  }
});