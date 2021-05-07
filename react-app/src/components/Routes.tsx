import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';
import Login from './Login';
import ProtectedRoute from './utils/ProtectedRoute';
import Layout from './utils/Layout';
import LeavesView from './LeavesView';

const homeWithLayout = () => (
  <Layout>
    <Home />
  </Layout>
);

const leavesWithLayout = () => (
  <Layout>
    <LeavesView />
  </Layout>
);

const Routes: React.FC = () => {
  return (
    <Router>
      <Switch>
        <PrivateRoute exact path="/" component={homeWithLayout} />
        <PrivateRoute exact path="/leaves" component={leavesWithLayout} />
        <Route exact path="/logout" component={Logout} />
        <ProtectedRoute exact path="/login" component={Login} />
      </Switch>
    </Router>
  );
};

export default Routes;
