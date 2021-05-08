import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { UseFormRegister } from 'react-hook-form';

import UserFormField from './UserFormField';
import krzysiomisio from '../../../assets/krzysiomisio.jpg';

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(16),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    width: theme.spacing(15),
    height: theme.spacing(15),
    margin: theme.spacing(1),
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(3),
  },
  menu: {
    width: '100%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  progress: {
    color: theme.palette.primary.dark,
  },
}));

interface Props {
  disabled: boolean;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  serverError: string;
}

const ProfileForm: React.FC<Props> = ({ disabled, register, handleSubmit, serverError }: Props) => {
  const classes = useStyles();

  return (
    <>
      <Avatar className={classes.avatar} src={krzysiomisio} />
      <form className={classes.form} noValidate onSubmit={handleSubmit}>
        <Grid container justify="space-between" spacing={2}>
          <UserFormField register={register} fieldName="firstname" textName="Name" disabled={disabled} />
          <UserFormField register={register} fieldName="lastname" textName="Surname" disabled={disabled} />
          <UserFormField register={register} fieldName="email" textName="Email" disabled={disabled} />
          <UserFormField register={register} fieldName="phoneNumber" textName="Phone" disabled={disabled} />
          <UserFormField register={register} fieldName="address" textName="Address" disabled={disabled} />
          <UserFormField register={register} fieldName="birthdate" textName="Birthdate" disabled={disabled} />
        </Grid>
        <Button
          type="submit"
          disabled={disabled}
          fullWidth
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          Submit
        </Button>
      </form>
      <Typography color="error">{serverError}</Typography>
    </>
  );
};

export default ProfileForm;
