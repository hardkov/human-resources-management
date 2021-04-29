import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import CircularProgress from "@material-ui/core/CircularProgress";



const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(16),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.primary.dark,
  },
  form: {
    width: "100%",
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  progress: {
    color: theme.palette.primary.dark,
  },
}));

const LoginForm = (
    // {
                     // isLoading,
                     // errorMessage,
                     // handleCredentialsChange,
                     // handleSubmit,
                   // }
                   ) => {
  const classes = useStyles();

  const isLoading = false;

  return (
        <Container component="main" maxWidth="xs">
          <div className={classes.paper}>
            <Typography component="h1" variant="h5">
              Login
            </Typography>
            {isLoading ? (
                <CircularProgress className={classes.progress} />
            ) : (
                 <form className={classes.form} noValidate
                       // onSubmit={handleSubmit}
                 >
                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <TextField
                          name="username"
                          variant="outlined"
                          required
                          fullWidth
                          id="username"
                          label="Username"
                          // onChange={handleCredentialsChange}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          variant="outlined"
                          required
                          fullWidth
                          name="password"
                          label="Password"
                          type="password"
                          id="password"
                          // onChange={handleCredentialsChange}
                      />
                    </Grid>
                  </Grid>
                  <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      color="primary"
                      className={classes.submit}
                  >
                    Login
                  </Button>
                </form>
            )}
          </div>
        </Container>
  );
};

export default LoginForm;