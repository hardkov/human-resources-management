import React from 'react';
import { Link } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core';

import MenuItem from '../models/MenuItem';

const useStyles = makeStyles(() => ({
  card: {
    position: 'relative',
    height: '300px',
    width: '100%',
  },
  cardActions: {
    position: 'absolute',
    right: 0,
    bottom: 0,
  },
}));

const HomeMenuItem: React.FC<MenuItem> = ({ img, title, description, link }: MenuItem) => {
  const classes = useStyles();

  return (
    <Card className={classes.card}>
      <CardActionArea>
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
      <CardActions className={classes.cardActions}>
        <Button variant="contained" size="small" color="secondary" component={Link} to={link}>
          Manage
        </Button>
      </CardActions>
    </Card>
  );
};

export default HomeMenuItem;
