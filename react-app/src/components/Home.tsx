import React from 'react';
import { makeStyles } from '@material-ui/core';

import HomeMenu from './HomeMenu';

const useStyles = makeStyles((theme) => ({
  container: {
    marginTop: theme.spacing(10),
  },
}));
const Home: React.FC = () => {
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <HomeMenu />
    </div>
  );
};

export default Home;
