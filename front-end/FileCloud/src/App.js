import React, { useEffect } from "react";
import FileViewer from "./features/file/components/FileViewer";
import AppRouter from "./router/index"
import { useDispatch, useSelector } from "react-redux";
import { checkAuthStatus } from "./features/authenticate/authThunk";
import ProtectedLayout from "./layouts/ProtectedLayout"

function App() {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.user);
    const authStatus = useSelector((state) => state.auth.isAuthenticated);
    useEffect(()=> {
        if(authStatus === null){
        // dispatch(checkAuthStatus());
    }
    }, [])
    // if (!authStatus) return <ProtectedLayout/>
    // else
    return (
        
            <div className="App">
             <AppRouter/>
                  <FileViewer /> 
            </div>
    );
}

export default App;
