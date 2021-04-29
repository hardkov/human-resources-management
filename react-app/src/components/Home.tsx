import React from 'react';
import { makeStyles } from '@material-ui/core';

import HomeMenu from './HomeMenu';
import Login from "./Login";

const useStyles = makeStyles((theme) => ({
  container: {
    marginTop: theme.spacing(10),
  },
}));
const Home: React.FC = () => {
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Login />
    </div>
  );
};

export default Home;
