import React, { useEffect } from "react";
import FileViewer from "./features/file/components/FileViewer";
import AppRouter from "./router/landing-router"
import { useDispatch, useSelector } from "react-redux";
import { checkAuthStatus } from "./features/authenticate/authThunk";

function App() {
    const dispatch = useDispatch();
    // const user = useSelector((state) => state.auth.user);
    const authStatus = useSelector((state) => state.auth.isAuthenticated);
    useEffect(()=> {
        if(authStatus === null){
        dispatch(checkAuthStatus());
    }
    }, [])
    
    return (
        
            <div className="App">
             <AppRouter/>
            </div>
    );
}

export default App;
