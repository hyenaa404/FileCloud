import { configureStore } from "@reduxjs/toolkit";
import fileReducer from "./fileSlide";
const store = configureStore({
    reducer: {
        file: fileReducer,
    },
});

export default store;
