import React from 'react';
import { CssBaseline, ThemeProvider, createMuiTheme } from '@material-ui/core';

import Routes from './components/Routes';
import defaultTheme from './theme/defaultTheme.json';

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
