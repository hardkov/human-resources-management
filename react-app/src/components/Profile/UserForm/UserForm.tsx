import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { UseFormRegister } from 'react-hook-form';

import UserFormField from './UserFormField';

const useStyles = makeStyles((theme) => ({
  avatar: {
    width: theme.spacing(15),
    height: theme.spacing(15),
    margin: theme.spacing(1),
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

interface Props {
  disabled: boolean;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  success: boolean;
  serverMessage: string;
}

const UserForm: React.FC<Props> = ({ disabled, register, handleSubmit, success, serverMessage }: Props) => {
  const classes = useStyles();

  return (
    <>
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
      <Typography color={success ? 'primary' : 'error'}>{serverMessage}</Typography>
    </>
  );
};

export default UserForm;
