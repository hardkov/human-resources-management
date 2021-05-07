import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';
import Login from './Login';
import UserInsertForm from './UserInsertForm';
import ProtectedRoute from './utils/ProtectedRoute';
import UserInsert from "./UserInsert";

const Routes: React.FC = () => {
  return (
    <Router>
      <Switch>
        <PrivateRoute path="/home" component={Home} />
        <ProtectedRoute exact path="/" component={Login} />
        <Route exact path="/logout" component={Logout} />
        <Route exact path="/add" component={UserInsert} />
      </Switch>
    </Router>
  );
};

export default Routes;
