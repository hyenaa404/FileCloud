import { createAsyncThunk, isRejectedWithValue } from "@reduxjs/toolkit";
import { loginAPI, registerAPI, logoutAPI, authenticateAPI } from "./authAPI";


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

// export const checkAuthStatus = createAsyncThunk("auth/check-status", async() => {
//     const response = await authenticateAPI();
//     return response.data
// })

export const checkAuthStatus = createAsyncThunk(
    "auth/checkAuthStatus",
    async () => {
        const res = await authenticateAPI();
        // const data = res.json();
        return res.data;
        // else return thunkAPI.rejectWithValue({ message: "unauthorized" });
      
    }
  );
  