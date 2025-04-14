import { createSlice } from "@reduxjs/toolkit";
import { login, logout, register } from "./authThunk";

const authSlice = createSlice({
    name: auth,
    initialState: { user: null, status: "idle" },
        reducers: {},           //different
        extraReducers: (builder) => {
            builder
            // login action
                .addCase(login.pending, (state) => {
                    state.status = "loading";
                })
                .addCase(login.fulfilled, (state, action) => {
                    state.status = "succeeded";
                    state.user = action.payload.data;
                    console.log(action.payload.data)
                })
                .addCase(login.rejected, (state, action) => {
                    console.log("error code: " + action.payload.status)
                    state.status = "failed";
                })
            // register action
                .addCase(register.pending,(state) => {
                    state.status = "loading"
                })
                .addCase(register.fulfilled,(state, action)=> {
                    state.status = "succeeded"
                    state.user = action.payload.data
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
                })
                .addCase(logout.rejected,(state, action)=>{
                    state.status = "failed"
                    console.log("error code: " + action.payload.status)
                })
        },
    });

    export default authSlice.reducer