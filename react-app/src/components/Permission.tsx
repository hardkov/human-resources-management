import React, { useState } from 'react';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { makeStyles, Switch } from '@material-ui/core';

import PermissionData from '../types/PermissionData';
import { updatePermission } from '../services/userService';
import PermissionList from './PermissionList';

const useStyles = makeStyles(() => ({
  switch: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
}));

interface Props {
  permission: PermissionData;
  currentUserPermission: PermissionData;
}

const Permission: React.FC<Props> = ({ permission, currentUserPermission }: Props) => {
  const classes = useStyles();
  const [read, setRead] = useState<number[]>(permission.read);
  const [write, setWrite] = useState<number[]>(permission.write);
  const [add, setAdd] = useState<boolean>(permission.add);
  const [serverError, setServerError] = useState<string>('');

  const onClick = async (isPermissionRead: boolean, userId: number, isActionAdd: boolean) => {
    // deleting from write list or from write and read list at once
    const remove = () => {
      const newWrite = write.filter((item) => item !== userId);

      if (!isPermissionRead) return { newRead: read, newWrite };

      const newRead = read.filter((item) => item !== userId);

      return { newRead, newWrite };
    };

    // adding to read list of read and write list at once
    const addIfNotExist = () => {
      let newWrite = write;
      if (!write.includes(userId)) newWrite = [...write, userId];

      let newRead = read;
      if (!read.includes(userId)) newRead = [...read, userId];

      if (isPermissionRead) return { newRead, newWrite: write };

      return { newRead, newWrite };
    };

    const actionFunc = isActionAdd ? addIfNotExist : remove;

    const { newRead, newWrite } = actionFunc();

    const permissionCopy = { ...permission, write: newWrite, read: newRead, add };

    const result = await updatePermission(permissionCopy);

    if (result.success) {
      setServerError('');
      setWrite(newWrite);
      setRead(newRead);
    } else if (result.errors) {
      setServerError(result.errors[0]);
    }
  };

  const onChange = async () => {
    const permissionCopy = { ...permission, write, read, add: !add };

    const result = await updatePermission(permissionCopy);

    if (result.success) {
      setServerError('');
      setAdd(!add);
    } else if (result.errors) {
      setServerError(result.errors[0]);
    }
  };

  return (
    <Container component="main" maxWidth="sm">
      <Grid container spacing={2} direction="row">
        <Grid item xs={6}>
          <PermissionList
            isPermissionRead
            label="Read permissions"
            list={read}
            listSelect={currentUserPermission.read.filter((el) => !read.includes(el))}
            onClick={onClick}
          />
        </Grid>
        <Grid item xs={6}>
          <PermissionList
            isPermissionRead={false}
            label="Write permissions"
            list={write}
            listSelect={currentUserPermission.write.filter((el) => !write.includes(el))}
            onClick={onClick}
          />
        </Grid>
      </Grid>
      <div className={classes.switch}>
        <Typography color="textSecondary">User adding permission</Typography>
        <Switch checked={add} onChange={onChange} />
      </div>
      <Typography align="center" color="error">
        {serverError}
      </Typography>
    </Container>
  );
};

export default Permission;
