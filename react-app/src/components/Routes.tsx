import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';
import Login from './Login';
import ProtectedRoute from './utils/ProtectedRoute';
import Layout from './utils/Layout';
import LeavesView from './LeavesView';
import MyLeavesView from './MyLeavesView';
import EmployeesList from './EmployeesList';
import MyProfile from './MyProfile';
import EmployeeProfile from './EmployeeProfile';
import EarningsView from './EarningsView';
import MyEarningsView from './MyEarningsView';
import CreateApplication from './CreateApplication';
import UserInsert from './UserInsert';
import EmployeeAplications from './EmployeeAplications';
import MyApplications from './MyApplications';

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

const myLeavesWithLayout = () => (
  <Layout>
    <MyLeavesView />
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

const myEarningsViewWithLayout = () => (
  <Layout>
    <MyEarningsView />
  </Layout>
);

const createApplicationWithLayout = () => (
  <Layout>
    <CreateApplication />
  </Layout>
);

const userInsertWithLayout = () => (
  <Layout>
    <UserInsert />
  </Layout>
);

const applicationsWithLayout = () => (
  <Layout>
    <EmployeeAplications />
  </Layout>
);

const myApplicationsWithLayout = () => (
  <Layout>
    <MyApplications />
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
        <PrivateRoute exact path="/my-leaves" component={myLeavesWithLayout} />
        <PrivateRoute exact path="/employees" component={employeesListWithLayout} />
        <PrivateRoute exact path="/earnings" component={earningsViewWithLayout} />
        <PrivateRoute exact path="/my-earnings" component={myEarningsViewWithLayout} />
        <PrivateRoute exact path="/employees/insert" component={userInsertWithLayout} />
        <PrivateRoute exact path="/create-application" component={createApplicationWithLayout} />
        <PrivateRoute exact path="/applications" component={applicationsWithLayout} />
        <PrivateRoute exact path="/my-applications" component={myApplicationsWithLayout} />
      </Switch>
    </Router>
  );
};

export default Routes;
