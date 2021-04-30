import React from 'react';
import { Redirect, Route, RouteProps } from 'react-router-dom';
import { isLoggedIn } from '../../services/authService';

const PrivateRoute = (routeProps: RouteProps) => {
  if (isLoggedIn()) {
    return <Route {...routeProps} />;
  }

  return <Redirect to="/" />;
};

export default PrivateRoute;
