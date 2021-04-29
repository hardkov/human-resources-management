import React from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, IconButton, Button } from '@material-ui/core';
import HomeIcon from '@material-ui/icons/Home';
import { makeStyles } from '@material-ui/core/styles';

import ButtonData from '../types/ButtonData';
import useHeader from '../hooks/useHeader';

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
    color: theme.palette.primary.light,
  },
  button: {
    margin: theme.spacing(2),
  },
  iconButton: {
    color: theme.palette.primary.light,
  },
}));

const Header: React.FC = () => {
  const classes = useStyles();
  const buttons: ButtonData[] = useHeader();

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <IconButton className={classes.iconButton} component={Link} to="/">
            <HomeIcon />
          </IconButton>
          <div className={classes.buttonContainer}>
            {buttons.map((button) => (
              <Button
                className={classes.button}
                variant="contained"
                color="secondary"
                component={Link}
                to={button.link}
              >
                {button.text}
              </Button>
            ))}
          </div>
          <Typography className={classes.username} variant="h5">
            Admin
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default Header;
