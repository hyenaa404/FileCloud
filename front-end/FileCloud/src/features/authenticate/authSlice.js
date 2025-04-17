import { createSlice } from "@reduxjs/toolkit";
import { login, logout, register, checkAuthStatus } from "./authThunk";

const authSlice = createSlice({
    name: "auth",
    initialState: { user: null, isAuthenticated: null, status: "idle", error: "idle" },
        reducers: {},           // handle change in local UI
        // extraReducer + createAsyncThunk: handle download from/ upload to server
        extraReducers: (builder) => {
            builder
            // login action
                .addCase(login.pending, (state) => {
                    state.status = "loading";
                })
                .addCase(login.fulfilled, (state, action) => {
                    state.status = "succeeded";
                    state.user = action.payload.data;
                    state.isAuthenticated = true;
                    state.error = null;
                    console.log("isAuthenticated: " + state.isAuthenticated)
                })
                .addCase(login.rejected, (state, action) => {
                    console.log("error code: " + action.payload.status)
                    state.status = "failed";
                    state.error = action.payload.status
                })
            // register action
                .addCase(register.pending,(state) => {
                    state.status = "loading"
                })
                .addCase(register.fulfilled,(state, action)=> {
                    state.status = "succeeded"
                    state.user = action.payload.data
                    state.error = null
                    console.log(state.user)
                })   
                .addCase(register.rejected, (state, action)=>{
                    console.log("error code: " + action.payload.status)
                    state.status = "failed";
                })

                //logout action
                .addCase(logout.pending, (state) => {
                    state.status = "loading";
                })
                .addCase(logout.fulfilled, (state, action)=>{
                    state.status="succeeded";
                    state.isAuthenticated = false;
                    state.user = null;
                    state.error = null;
                    console.log("success : " + action.payload)
                })
                .addCase(logout.rejected,(state, err)=>{
                    state.status = "failed"
                    console.log("error code: " + err)
                })

                //check authenticate status
                .addCase(checkAuthStatus.pending, (state)=> {
                    state.status = "pending"
                })
                .addCase(checkAuthStatus.fulfilled, (state, action)=> {
                    state.isAuthenticated = action.payload.isAuthenticated 
                    state.user = action.payload.user
                    state.status = "succeeded"
                    console.log(action.payload)
                    state.error = null;
                })
                .addCase (checkAuthStatus.rejected, (state, action)=> {
                    state.status = "failed"
                    console.log(state.status)
                })
        },
    });

    export default authSlice.reducer