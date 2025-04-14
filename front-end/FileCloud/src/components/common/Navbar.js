import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/layout.css';


const Navbar = () => {
    return (
      <nav className="navbar">
        <Link to="/">Home</Link>
        <Link to="/setting">Setting</Link>
        <Link to="/login">Login</Link>
      </nav>
    );
  };
  
  export default Navbar;