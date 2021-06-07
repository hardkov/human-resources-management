import React from 'react';
import { Grid, makeStyles } from '@material-ui/core';

import HomeMenuItem from './HomeMenuItem';
import useHomeMenu from '../hooks/useHomeMenu';
import HomeMenuItemData from '../types/HomeMenuItemData';

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
    margin: 0,
  },
}));

const HomeMenu: React.FC = () => {
  const classes = useStyles();
  const menuItems: HomeMenuItemData[] = useHomeMenu();

  return (
    <Grid className={classes.grid} spacing={6} container justify={menuItems.length < 4 ? 'center' : 'flex-start'}>
      {menuItems.map((item) => (
        <Grid key={item.img} item xs={4}>
          <HomeMenuItem img={item.img} title={item.title} description={item.description} link={item.link} />
        </Grid>
      ))}
    </Grid>
  );
};

export default HomeMenu;
