import React from 'react';
import { Link } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import { makeStyles } from '@material-ui/core';

import HomeMenuItemData from '../types/HomeMenuItemData';

const useStyles = makeStyles(() => ({
  card: {
    position: 'relative',
    height: '220px',
    width: '100%',
  },
  cardActions: {
    position: 'absolute',
    right: 0,
    bottom: 0,
  },
}));

const HomeMenuItem: React.FC<HomeMenuItemData> = ({ img, title, description, link }: HomeMenuItemData) => {
  const classes = useStyles();

  return (
    <Card className={classes.card}>
      <CardActionArea component={Link} to={link}>
        <CardMedia component="img" alt="applicationImg" height="100" image={img} />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {title}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {description}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default HomeMenuItem;
