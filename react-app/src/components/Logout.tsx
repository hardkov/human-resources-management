import React, { useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { logout } from '../services/authService';

const Logout = () => {
  useEffect(() => {
    logout();
  });

  return <Redirect to="/login" />;
};

export default Logout;
