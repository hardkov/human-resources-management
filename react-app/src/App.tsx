import React from 'react';
import { CssBaseline, ThemeProvider, createMuiTheme } from '@material-ui/core';

import Routes from './components/Routes';
import defaultTheme from './theme/defaultTheme.json';

// import images at high-level component in order to cache it
/* eslint-disable */
import applicationImg from '../assets/applications.jpg';
import employeesImg from '../assets/employees.jpg';
import logsImg from '../assets/logs.png';
import myApplicationsImg from '../assets/myApplications.jpg';
import createApplicationsImg from '../assets/createApplication.png';
import earningsImg from '../assets/earnings.jpg';
import myEarningsImg from '../assets/myEarnings.jpeg';
import leavesImg from '../assets/leaves.jpg';
import myLeavesImg from '../assets/myLeaves.jpg';
/* eslint-enable */

const theme = createMuiTheme(defaultTheme);

const App: React.FC = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Routes />
    </ThemeProvider>
  );
};

export default App;
