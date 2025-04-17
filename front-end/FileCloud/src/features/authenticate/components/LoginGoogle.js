import { useDispatch, useSelector } from "react-redux";
import { checkAuthStatus, login } from "../authThunk";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export const LoginGoogle = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    // const isAuthenticated = useSelector((state)=> state.auth.isAuthenticated)
    useEffect(()=> {
        dispatch(checkAuthStatus())
        navigate("/home")
    }, [])
    return null
}