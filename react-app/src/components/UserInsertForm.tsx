import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { UseFormRegister, UseFormSetValue } from 'react-hook-form';

import UserFormField from './Profile/UserForm/UserFormField';
import UserFormDate from './Profile/UserForm/UserFormDate';
import UserFormPasswordField from "./Profile/UserForm/UserFormPasswordField";

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
  setValue: UseFormSetValue<any>;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  serverError: string;
}

const UserInsertForm: React.FC<Props> = ({ disabled, register, setValue, handleSubmit, serverError }: Props) => {
  const classes = useStyles();

  return (
    <>
      <form className={classes.form} noValidate onSubmit={handleSubmit}>
        <Grid container justify="space-between" spacing={2}>
          <UserFormField register={register} fieldName="personalData.firstname" textName="Name" disabled={disabled} />
          <UserFormField register={register} fieldName="personalData.lastname" textName="Surname" disabled={disabled} />
          <UserFormField register={register} fieldName="personalData.email" textName="Email" disabled={disabled} />
          <UserFormField register={register} fieldName="personalData.phoneNumber" textName="Phone" disabled={disabled} />
          <UserFormField register={register} fieldName="personalData.address" textName="Address" disabled={disabled} />
          <UserFormDate setValue={setValue} fieldName="personalData.birthdate" textName="Birthdate" disabled={disabled} />
          <UserFormField register={register} fieldName="username" textName="Username" disabled={disabled} />
          <UserFormPasswordField register={register} fieldName="password" textName="Password" disabled={disabled} />
          <UserFormField register={register} fieldName="position" textName="Position" disabled={disabled} />
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

export default UserInsertForm;
