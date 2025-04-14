import React from "react";
import { Link } from "react-router-dom";

const LandingPage = () => {
  return (
    <div style={{ padding: "2rem", textAlign: "center" }}>
      <h1>Welcome to File Management App</h1>
      <p>Manage, share, and organize your files easily.</p>
      <div style={{ marginTop: "1rem" }}>
        <Link to="/login" style={{ marginRight: "1rem" }}>Login</Link>
        <Link to="/register">Register</Link>
      </div>
    </div>
  );
};

export default LandingPage;
