import React from 'react';
import { Redirect, Route, RouteProps } from 'react-router-dom';
import { isLoggedIn } from '../../services/authService';

const ProtectedRoute = (routeProps: RouteProps) => {
  if (isLoggedIn()) {
    return <Redirect to="/home" />;
  }

  return <Route {...routeProps} />;
};

export default ProtectedRoute;
