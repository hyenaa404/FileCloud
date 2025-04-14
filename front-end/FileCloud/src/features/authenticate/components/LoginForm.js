import { useDispatch, useSelector } from "react-redux";
import { login } from "../authThunk";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function LoginForm() {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.auth.user);
    const status = useSelector((state) => state.auth.status);
    const error = useSelector((state) => state.auth.error)
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        dispatch(login({ email, password }));
    };

    useEffect(() => {

        console.log("status: " + status)
        if (status === "succeeded") {
            console.log("login success" + user.fullName)
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
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            <button type="submit">Login</button>
        </form>
    );
}
