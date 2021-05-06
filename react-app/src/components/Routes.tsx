import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';
import Login from './Login';
import ProtectedRoute from './utils/ProtectedRoute';
import Layout from './utils/Layout';

const homeWithLayout = () => (
  <Layout>
    <Home />
  </Layout>
);

const Routes: React.FC = () => {
  return (
    <Router>
      <Switch>
        <PrivateRoute exact path="/" component={homeWithLayout} />
        <Route exact path="/logout" component={Logout} />
        <ProtectedRoute exact path="/login" component={Login} />
      </Switch>
    </Router>
  );
};

export default Routes;
