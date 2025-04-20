import React from "react";
import { Link } from "react-router-dom";
import "../styles/landing-page.css"

const LandingPage = () => {
  return (
    <div className="landing-wrapper">
      <div className="landing-overlay">
        <div className="landing-content">
          <h1>Welcome to File Management App</h1>
          <p>Manage, share, and organize your files easily and securely.</p>
          <div className="landing-buttons">
            <Link to="/login" className="btn-primary">Login</Link>
            <Link to="/register" className="btn-secondary">Register</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
