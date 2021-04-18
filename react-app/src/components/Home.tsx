import React from 'react';
import { Container, Grid, Paper, Typography, Box, makeStyles } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  grid: {
    marginTop: theme.spacing(16),
  },
}));

const Home: React.FC = () => {
  const classes = useStyles();

  return (
    <Grid className={classes.grid} container spacing={6} direction="row" justify="center" alignItems="center">
      <Grid item xs={3}>
        <Container component={Paper} elevation={3}>
          <Typography variant="overline" color="textPrimary">
            <Box fontWeight="fontWeightBold">LOGS</Box>
          </Typography>
        </Container>
      </Grid>
      <Grid item xs={3}>
        <Container component={Paper} elevation={3}>
          <Typography variant="overline" color="textPrimary">
            <Box fontWeight="fontWeightBold">EMPLOYEES</Box>
          </Typography>
        </Container>
      </Grid>
      <Grid item xs={3}>
        <Container component={Paper} elevation={3}>
          <Typography variant="overline" color="textPrimary">
            <Box fontWeight="fontWeightBold">APPLICATIONS</Box>
          </Typography>
        </Container>
      </Grid>
    </Grid>
  );
};

export default Home;
