import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './Home';
import Header from './Header';
import PrivateRoute from './utils/PrivateRoute';
import Logout from './Logout';

const Routes: React.FC = () => {
  return (
    <Router>
      <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/logout" component={Logout} />
      </Switch>
    </Router>
  );
};

export default Routes;
