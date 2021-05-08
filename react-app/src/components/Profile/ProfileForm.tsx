import React from 'react';
import Container from '@material-ui/core/Container';
import { UseFormRegister } from 'react-hook-form';
import { Switch } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import UserForm from '../UserForm/UserForm';

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
    justifyContent: 'space-between',
  },
  switch: {
    display: 'flex',
    alignItems: 'center',
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  progress: {
    color: theme.palette.primary.dark,
  },
}));

interface Props {
  setChangeMode: React.Dispatch<React.SetStateAction<boolean>>;
  changeMode: boolean;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  serverError: string;
}

const ProfileForm: React.FC<Props> = ({ setChangeMode, changeMode, register, handleSubmit, serverError }: Props) => {
  const classes = useStyles();

  return (
    <Container component="main" maxWidth="xs">
      <div className={classes.paper}>
        <div className={classes.menu}>
          <Button>
            <ArrowBackIcon />
          </Button>
          <div className={classes.switch}>
            <Typography color="textSecondary">Enable change</Typography>
            <Switch checked={changeMode} onChange={() => setChangeMode(!changeMode)} />
          </div>
        </div>
        <UserForm disabled={!changeMode} register={register} handleSubmit={handleSubmit} serverError={serverError} />
      </div>
    </Container>
  );
};

export default ProfileForm;
