
import React from 'react';
import { Link, Outlet } from 'react-router-dom';

const MainLayout = () => {
  return (
    <div>
      <nav style={{ padding: '1rem', background: '#eee' }}>
        <Link to="/" style={{ marginRight: '1rem' }}>Home</Link>
        <Link to="/Setting">Setting</Link>
      </nav>

      <main style={{ padding: '1rem' }}>
        <Outlet />
      </main>
    </div>
  );
};

export default MainLayout;
