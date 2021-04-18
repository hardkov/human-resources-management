import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { AppBar, Toolbar, Typography, IconButton, Button } from '@material-ui/core';
import HomeIcon from '@material-ui/icons/Home';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },

  buttonContainer: {
    display: 'flex',
    flex: 1,
    justifyContent: 'flex-end',
  },

  username: {
    marginLeft: theme.spacing(2),
  },
  button: {
    margin: theme.spacing(2),
  },
}));

const Header: React.FC = () => {
  const classes = useStyles();
  const buttons: string[] = ['LOGOUT'];

  return (
    <div className={classes.root}>
      <AppBar position="fixed">
        <Toolbar>
          <IconButton component={RouterLink} to="/">
            <HomeIcon />
          </IconButton>
          <div className={classes.buttonContainer}>
            {buttons.map((button) => (
              <Button className={classes.button} variant="contained" color="secondary">
                {button}
              </Button>
            ))}
          </div>
          <Typography className={classes.username} color="textPrimary" variant="h5">
            ADMIN
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default Header;
