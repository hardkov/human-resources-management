import React from 'react';
import { Link } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import { UseFormRegister } from 'react-hook-form';
import { IconButton, Switch } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import Typography from '@material-ui/core/Typography';
import UserForm from './UserForm/UserForm';

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(16),
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

interface Props {
  setChangeMode: React.Dispatch<React.SetStateAction<boolean>>;
  changeMode: boolean;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  success: boolean;
  serverMessage: string;
  referer?: string;
  disabled?: boolean;
}

const ProfileForm: React.FC<Props> = ({
  setChangeMode,
  changeMode,
  register,
  handleSubmit,
  success,
  serverMessage,
  referer,
  disabled,
}: Props) => {
  const classes = useStyles();

  return (
    <Container component="main" maxWidth="sm">
      <div className={classes.paper}>
        <div className={classes.menu}>
          <IconButton
            color="secondary"
            style={{ padding: 0, justifyContent: 'flex-start' }}
            component={Link}
            to={referer || '/'}
          >
            <ArrowBackIcon />
          </IconButton>
          {!disabled && (
            <div className={classes.switch}>
              <Typography color="textSecondary">Enable change</Typography>
              <Switch checked={changeMode} onChange={() => setChangeMode(!changeMode)} />
            </div>
          )}
        </div>
        <UserForm
          disabled={disabled || !changeMode}
          register={register}
          handleSubmit={handleSubmit}
          success={success}
          serverMessage={serverMessage}
        />
      </div>
    </Container>
  );
};

export default ProfileForm;
