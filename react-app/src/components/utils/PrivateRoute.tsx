import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { isLoggedIn } from '../../services/authService';

const PrivateRoute = (routeProps: any) => {
  if (isLoggedIn()) {
    return <Route {...routeProps} />;
  }

  return <Redirect to="/login" />;
};

export default PrivateRoute;
