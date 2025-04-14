import { configureStore } from "@reduxjs/toolkit";
import fileReducer from "../features/file/fileSlice";
// import { composeWithDevTools } from 'redux-devtools-extension';
const store = configureStore({
    reducer: {
        file: fileReducer,
    }, 
});

export default store;
