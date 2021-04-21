import React from 'react';
import { Grid, makeStyles } from '@material-ui/core';

import HomeMenuItem from './HomeMenuItem';
import applicationImg from '../assets/applications.png';
import employeesImg from '../assets/employees.jpg';
import logsImg from '../assets/logs.png';

const useStyles = makeStyles(() => ({
  root: {
    position: 'relative',
    height: '300px',
    width: '100%',
  },
  cardActions: {
    position: 'absolute',
    right: 0,
    bottom: 0,
  },
  grid: {
    width: '100%',
  },
}));

const HomeMenu: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid className={classes.grid} spacing={6} container direction="row" justify="center" alignItems="center">
      <Grid item xs={3}>
        <HomeMenuItem
          img={logsImg}
          title="Logs"
          description="See all the data modification done by system users."
          link="/"
        />
      </Grid>
      <Grid item xs={3}>
        <HomeMenuItem
          img={employeesImg}
          title="Employees"
          description="See all the employees. Manage their permission and get insight into user data details."
          link="/"
        />
      </Grid>
      <Grid item xs={3}>
        <HomeMenuItem
          img={applicationImg}
          title="Applications"
          description="See all applications from the users and accept or reject them."
          link="/"
        />
      </Grid>
    </Grid>
  );
};

export default HomeMenu;
