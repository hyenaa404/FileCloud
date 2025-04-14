import { createAsyncThunk, isRejectedWithValue } from "@reduxjs/toolkit";
import { loginAPI, registerAPI, logoutAPI } from "./authAPI";


export const login = createAsyncThunk("auth/login",async(data, {rejectWithValue}) => {
    try{
        const response = await loginAPI(data)
        return {
            data: response.data.user
        };
    }catch(err){
        return rejectWithValue({
            status: err.response?.status
        });
    }
});

export const register = createAsyncThunk("auth/register", async(data, {rejectWithValue}) => {
    try{
        const response = await registerAPI(data)
        return{
            data: response.data.user
        }
    }catch(err){
        return rejectWithValue({
            status: err.response?.status
        })
    }
})


export const logout = createAsyncThunk("auth/logout", async( {rejectWithValue}) => {
    try{
        const response = await logoutAPI();
        return response.status
    }catch(err){
        return rejectWithValue({
            status: err.response?.status
        })
    }
})