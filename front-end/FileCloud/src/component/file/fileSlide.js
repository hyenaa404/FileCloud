import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from 'axios';

// Async action để fetch file từ API
export const fetchFile = createAsyncThunk("file/fetchFile", async () => {
    const response = await axios.get("http://localhost:8080/FileCloud/file");
    return response.data.fileData; // Trả về Base64 string
});

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
                state.fileData = action.payload;
            })
            .addCase(fetchFile.rejected, (state) => {
                state.status = "failed";
            });
    },
});

export default  fileSlice.reducer;
