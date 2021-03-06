import React, { useEffect, useState } from 'react';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import AddIcon from '@material-ui/icons/Add';
import { makeStyles } from '@material-ui/core';

import IconButton from '@material-ui/core/IconButton';
import { Link } from 'react-router-dom';
import EmployeeRow from './EmployeeRow';
import UserData from '../types/UserData';
import ActionResult from '../types/ActionResult';
import { getAllUsers } from '../services/userService';

const useStyles = makeStyles((theme) => ({
  pageContent: {
    marginRight: '20px',
    marginLeft: '20px',
  },
  add2: {
    margin: theme.spacing(1),
    position: 'absolute',
    right: '200px',
  },
}));

const EmployeesList = () => {
  const classes = useStyles();

  const [users, setUsers] = useState<UserData[] | undefined>([]);

  useEffect(() => {
    const apiCall = async () => {
      const fetchedUsers: ActionResult<UserData[]> = await getAllUsers();
      if (fetchedUsers.success) setUsers(fetchedUsers.data);
    };
    apiCall();
  }, []);
  return (
    <div className={classes.pageContent}>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <h1>Employee List </h1>
        <IconButton
          className={classes.add2}
          color="primary"
          component={Link}
          to={{ pathname: '/employees/insert', state: { referer: '/employees' } }}
        >
          <AddIcon />
        </IconButton>
      </div>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell>
                <b>Firstname</b>
              </TableCell>
              <TableCell>
                <b>Lastname</b>
              </TableCell>
              <TableCell>
                <b>Position</b>
              </TableCell>
              <TableCell>
                <b>Employee profile</b>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users?.map((user) => (
              <EmployeeRow key={user.id} row={user} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};
export default EmployeesList;
