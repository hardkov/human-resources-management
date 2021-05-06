import React from 'react';
import { makeStyles } from '@material-ui/core';
import Header from '../Header';

const useStyles = makeStyles((theme) => ({
  pageContent: {
    marginTop: theme.spacing(10),
  },
}));

const Layout: React.FC<any> = ({ children }: any) => {
  const classes = useStyles();

  return (
    <div>
      <Header />
      <div className={classes.pageContent}>{children}</div>
    </div>
  );
};

export default Layout;
