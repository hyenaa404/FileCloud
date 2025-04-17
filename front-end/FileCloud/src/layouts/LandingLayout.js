
import React from 'react';
import {Link, Outlet } from 'react-router-dom';
import {LandingNavbar} from '../components/common/Navbar';

const LandingLayout = () => {
  return (
    <div>
      <LandingNavbar />
      <main className="main-content">
        <Outlet />
      </main>
    </div>
  );
};

export default LandingLayout;
