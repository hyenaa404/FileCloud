import { configureStore } from "@reduxjs/toolkit";
import fileReducer from "../features/file/fileSlice";
import authReducer from "../features/authenticate/authSlice"
import forderReducer from "../features/folder/folderSlice"
// import { composeWithDevTools } from 'redux-devtools-extension';
const store = configureStore({
    reducer: {
        file: fileReducer,
        auth: authReducer,
        folder: forderReducer,
    }, 
});

export default store;
