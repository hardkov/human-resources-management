import React from 'react';
import { CssBaseline } from '@material-ui/core';

import Routes from './components/Routes';

const App: React.FC = () => {
  return (
    <>
      <CssBaseline />
      <Routes />
    </>
  );
};

export default App;
