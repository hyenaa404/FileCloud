import React from "react";
import { Link } from "react-router-dom";
import "../styles/landing-page.css"
import { useSelector } from "react-redux";

const LandingPage = () => {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
    

  const ButtonGroup1 = () => (
    <>
      <Link to="/login" className="btn-secondary">Login</Link>
      <Link to="/register" className="btn-secondary">Register</Link>
    </>
  );
  
  const ButtonGroup2 = () => (
    <>
      <Link to="/logout" className="btn-secondary">Logout</Link>
      <Link to="/home" className="btn-secondary">Home</Link>
    </>
  );
  return (
    <div className="landing-wrapper">
      <div className="landing-overlay">
        <div className="landing-content">
          <h1>Welcome to File Management App</h1>
          <p>Manage, share, and organize your files easily and securely.</p>
          <div className="landing-buttons">
            {isAuthenticated === true? <ButtonGroup2/>: <ButtonGroup1/>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
