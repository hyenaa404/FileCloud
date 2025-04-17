import { useDispatch, useSelector } from "react-redux";
import { login } from "../authThunk";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import "../../../styles/login.css"
// import "themify-icons/themify-icons.css"

export default function LoginForm() {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.user);
    const status = useSelector((state) => state.auth.status);
    const error = useSelector((state) => state.auth.error)
    const [submitted, setSubmitted] = useState(false)
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        dispatch(login({ email, password }));
        setSubmitted("true")
    };

    useEffect(() => {
        if (!submitted) return;
        console.log("status: " + status)
        if (status === "succeeded") {
            console.log(user)
            navigate("/home")
        } else if (status === "failed") {
            console.log(error)
            if (error === 401) alert("Wrong login information");
            else if (error === 404) alert("Account does not exist");
            else alert("Error happened");
        }
    }, [status]);

    ///////////////////////////////////////////////////////////////////

    // const [submitted, setSubmitted] = useState(false);

    // const handleLogin = (e) => {
    //     e.preventDefault();
    //     dispatch(login({ email, password }));
    //     setSubmitted(true);
    // };


    // useEffect(() => {
    //     if (!submitted) return;

    //     if (status === "succeeded") {
    //         console.log("login success", user);
    //         // navigate("/"); 
    //     } else if (status === "failed") {
    //         if (error === 401) alert("Wrong login information");
    //         else if (error === 404) alert("Account does not exist");
    //         else alert("Login error");
    //     }
    // }, [status, submitted, error, navigate]);

    // const handleLogin = async (e) => {
    //     e.preventDefault();
    //     try {
    //       await dispatch(login({ email, password }));
    //       console.log("login success", user);
    //     //   navigate("/");
    //     } catch (err) {
    //       if (err === 401) alert("Wrong login information");
    //       else if (err === 404) alert("Account does not exist");
    //       else alert("Login error");
    //     }
    //   };

    return (
        <form onSubmit={handleLogin}>
            <h2>Login</h2>
            <label htmlFor="email" >Email: </label>
            <input id="email" type="email"value={email} onChange={(e) => setEmail(e.target.value)} required />
            <label htmlFor="password" >Password: </label>
            <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
            <button type="submit">Login</button>
            <a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/FileCloud/login-google&response_type=code
                     &client_id=800437652534-oivrrr4du8sdgtc3oh6cnk759rgmo86j.apps.googleusercontent.com&approval_prompt=force" class="social">
                <button type="button">Google</button></a>
        </form>
    );
}
