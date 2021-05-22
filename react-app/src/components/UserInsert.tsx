import React, { useEffect, useState } from 'react';

import { makeStyles } from '@material-ui/core/styles';
import { useForm } from 'react-hook-form';
import Container from '@material-ui/core/Container';
import { Link, useLocation } from 'react-router-dom';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import { IconButton } from '@material-ui/core';
import { addUser } from '../services/userService';
import UserInsertForm from './UserInsertForm';
import UserInsertionData from '../types/UserInsertionData';

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  menu: {
    width: '100%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  switch: {
    display: 'flex',
    alignItems: 'center',
  },
}));

interface LocationState {
  referer?: string;
}

const UserInsert = () => {
  const classes = useStyles();

  const location = useLocation<LocationState>();

  const { referer } = location.state;

  const [serverError, setServerError] = useState<string>('');
  const [user, setUser] = useState<UserInsertionData>({
    personalData: {
      id: 0,
      firstname: '',
      lastname: '',
      email: '',
      phoneNumber: '',
      address: '',
      birthdate: '',
      thumbnail: null,
    },
    position: '',
    username: '',
    password: '',
  });

  const { register, handleSubmit, reset, setValue } = useForm<UserInsertionData>({
    defaultValues: user,
  });

  useEffect(() => {
    reset(user);
    setServerError('');
  }, [user, reset]);

  const handleSubmitCallback = async (formData: any) => {
    if (user == null) return;

    const result = await addUser(formData);

    if (result.success) {
      return;
    }

    if (result.errors != null) setServerError(result.errors[0]);
  };

  return (
    <Container component="main" maxWidth="sm">
      <div className={classes.paper}>
        <div className={classes.menu}>
          <IconButton style={{ padding: 0, justifyContent: 'flex-start' }} component={Link} to={referer || '/'}>
            <ArrowBackIcon color="primary" />
          </IconButton>
        </div>
        <UserInsertForm
          register={register}
          setValue={setValue}
          handleSubmit={handleSubmit(handleSubmitCallback)}
          serverError={serverError}
          disabled={false}
        />
      </div>
    </Container>
  );
};

export default UserInsert;
