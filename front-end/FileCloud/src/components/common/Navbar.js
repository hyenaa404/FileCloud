import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/layout.css';


export const LandingNavbar = () => {
    return (
      <nav className="navbar">
        <Link to="/">Home</Link>
        <Link to="/register">Register</Link>
        <Link to="/login">Login</Link>
      </nav>
    );
  };
  

  export const HomeNavbar = () => {
    return (
      <nav className="navbar">
        <Link to="/home">Home</Link>
        <Link to="/file">File</Link>
        <Link to="/setting">Setting</Link>
      </nav>
    );
  };