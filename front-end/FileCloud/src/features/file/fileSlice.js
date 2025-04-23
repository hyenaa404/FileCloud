import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from 'axios';
import { fetchFile, uploadFile } from "./fileThunk";



export const fileSlice = createSlice({
    name: "file",
    initialState: { fileData: null, status: "idle" },
    reducers: {},           
    extraReducers: (builder) => {
        builder
            .addCase(fetchFile.pending, (state) => {
                state.status = "loading";
            })
            .addCase(fetchFile.fulfilled, (state, action) => {
                state.status = "succeeded";
                state.fileData = action.payload.data;
                console.log(action.payload.role)
            })
            .addCase(fetchFile.rejected, (state, action) => {
                console.log("error code: " + action.payload.status)
                state.status = "failed";
            })
            // Upload file
            .addCase(uploadFile.pending, (state) => {
                state.status = "uploading";
            })
            .addCase(uploadFile.fulfilled, (state, action) => {
                state.status = "uploaded";
                console.log("File uploaded successfully");
            })
            .addCase(uploadFile.rejected, (state, action) => {
                state.status = "failed";
            });
    },
});

export default  fileSlice.reducer;
