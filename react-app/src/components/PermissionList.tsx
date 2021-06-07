import React, { useState } from 'react';

import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import CloseIcon from '@material-ui/icons/Close';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Typography from '@material-ui/core/Typography';
import FormControl from '@material-ui/core/FormControl';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import { IconButton, makeStyles, MenuItem, Modal, Select } from '@material-ui/core';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import UserData from '../types/UserData';

const useStyles = makeStyles((theme) => ({
  invisibleButton: {
    opacity: 0,
  },

  listItemAvatar: {
    minWidth: 0,
  },

  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },

  paper: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[2],
    padding: theme.spacing(2, 4, 3),
  },
}));

interface Props {
  isPermissionRead: boolean;
  label: string;
  list: number[];
  listSelect: number[];
  usersData: any;
  onClick: (isPermissionRead: boolean, userId: number, isActionAdd: boolean) => void;
}

const PermissionList: React.FC<Props> = ({ isPermissionRead, label, list, listSelect, usersData, onClick }: Props) => {
  const classes = useStyles();
  const [selected, setSelected] = useState<number | ''>('');
  const [open, setOpen] = useState<boolean>(false);
  const [userData, setUserData] = useState<UserData>();

  const onClickWrapper = () => {
    if (selected !== '') {
      onClick(isPermissionRead, selected, true);
      setSelected('');
    }
  };

  const onClickProfile = async (userId: number) => {
    setOpen(true);
    setUserData(usersData[userId]);
  };

  return (
    <>
      <Modal className={classes.modal} open={open} onClose={() => setOpen(false)}>
        <div className={classes.paper}>
          <Typography>Name: {userData?.personalData.firstname}</Typography>
          <Typography>Surname: {userData?.personalData.lastname}</Typography>
          <Typography>Email: {userData?.personalData.email}</Typography>
          <Typography>Phone: {userData?.personalData.phoneNumber}</Typography>
          <Typography>Address: {userData?.personalData.address}</Typography>
          <Typography>Brithdate: {userData?.personalData.birthdate}</Typography>
        </div>
      </Modal>
      <List>
        <Typography variant="h6">{label}</Typography>
        {list.map((userId) => (
          <ListItem key={userId} disableGutters divider>
            <ListItemText>
              {usersData?.[userId].personalData.firstname} {usersData?.[userId].personalData.lastname}
            </ListItemText>
            <ListItemAvatar>
              <IconButton color="secondary" size="small" onClick={() => onClick(isPermissionRead, userId, false)}>
                <CloseIcon />
              </IconButton>
            </ListItemAvatar>
            <ListItemAvatar className={classes.listItemAvatar}>
              <IconButton onClick={() => onClickProfile(userId)} color="secondary" size="small">
                <AccountCircleIcon />
              </IconButton>
            </ListItemAvatar>
          </ListItem>
        ))}
        <ListItem disableGutters>
          <FormControl fullWidth>
            <Select onChange={(e: React.ChangeEvent<any>) => setSelected(e.target.value)} value={selected}>
              {listSelect.map((userId) => (
                <MenuItem key={userId} value={userId}>
                  {usersData?.[userId].personalData.firstname} {usersData?.[userId].personalData.lastname}
                </MenuItem>
              ))}
              <MenuItem value="" />
            </Select>
          </FormControl>
          {/* invisible icon to line up with content above */}
          <ListItemAvatar className={classes.listItemAvatar}>
            <IconButton className={classes.invisibleButton} color="secondary" size="small" disabled>
              <AddCircleIcon />
            </IconButton>
          </ListItemAvatar>
          <ListItemAvatar className={classes.listItemAvatar}>
            <IconButton color="secondary" size="small" onClick={onClickWrapper} disabled={selected == null}>
              <AddCircleIcon />
            </IconButton>
          </ListItemAvatar>
        </ListItem>
      </List>
    </>
  );
};

export default PermissionList;
