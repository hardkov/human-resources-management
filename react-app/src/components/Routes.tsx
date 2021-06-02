import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';
import Login from './Login';
import ProtectedRoute from './utils/ProtectedRoute';
import Layout from './utils/Layout';
import LeavesView from './LeavesView';
import EmployeesList from './EmployeesList';
import MyProfile from './MyProfile';
import EmployeeProfile from './EmployeeProfile';
import EarningsView from "./EarningsView";

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

const employeesListWithLayout = () => (
  <Layout>
    <EmployeesList />
  </Layout>
);

const myProfileWithLayout = () => (
  <Layout>
    <MyProfile />
  </Layout>
);

const employeeProfileWithLayout = () => (
  <Layout>
    <EmployeeProfile />
  </Layout>
);

const earningsViewWithLayout = () => (
    <Layout>
        <EarningsView />
    </Layout>
);

const Routes: React.FC = () => {
  return (
    <Router>
      <Switch>
        <Route exact path="/logout" component={Logout} />
        <ProtectedRoute exact path="/login" component={Login} />
        <PrivateRoute exact path="/" component={homeWithLayout} />
        <PrivateRoute exact path="/profile" component={myProfileWithLayout} />
        <PrivateRoute exact path="/employee-profile" component={employeeProfileWithLayout} />
        <PrivateRoute exact path="/leaves" component={leavesWithLayout} />
        <PrivateRoute exact path="/employees" component={employeesListWithLayout} />
        <PrivateRoute exact path="/earnings" component={earningsViewWithLayout} />
      </Switch>
    </Router>
  );
};

export default Routes;
