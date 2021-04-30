import React from 'react';
import { makeStyles } from '@material-ui/core';
import { BrowserRouter as Router, Route } from 'react-router-dom';

import HomeMenu from './HomeMenu';
import Header from './Header';

const useStyles = makeStyles((theme) => ({
  pageContent: {
    marginTop: theme.spacing(10),
  },
}));
const Home: React.FC = () => {
  const classes = useStyles();

  return (
    <>
      <Header />
      <div className={classes.pageContent}>
        <Router>
          <Route exact path="/home" component={HomeMenu} />
        </Router>
      </div>
    </>
  );
};

export default Home;
