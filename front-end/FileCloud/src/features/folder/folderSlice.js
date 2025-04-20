import { createSlice } from "@reduxjs/toolkit";
import { fetchFolder, uploadFile } from "./folderThunk";

export const folderSlice = createSlice({
    name: "folder",
    initialState: { fileList: null, folderList: null, status: "idle", uploadStatus: "idle" },
    reducers: {}, extraReducers: (builder) => {
        builder
            .addCase(fetchFolder.pending, (state) => {
                state.status = "loading";
            })
            .addCase(fetchFolder.fulfilled, (state, action) => {
                state.status = "succeeded";
                state.fileList = action.payload.listFile;
                state.folderList = action.payload.listFolder
                console.log("folderlist: " + state.folderList)
                console.log("succeeded")
            })
            .addCase(fetchFolder.rejected, (state, action) => {
                state.status = "failed"
                console.log("error code: " + action.payload.status)
            })
            // Upload file
            .addCase(uploadFile.pending, (state) => {
                state.uploadStatus = "uploading";
            })
            .addCase(uploadFile.fulfilled, (state, action) => {
                state.uploadStatus = "uploaded";
                console.log("File uploaded successfully");
            })
            .addCase(uploadFile.rejected, (state, action) => {
                state.uploadStatus = "failed";
                console.log("Upload error: " + action.payload.status);
            });
    },
})

export default folderSlice.reducer