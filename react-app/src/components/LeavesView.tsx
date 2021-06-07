import React, { useEffect, useState } from 'react';
import { DataGrid, GridCellParams } from '@material-ui/data-grid';
import { IconButton, Card, Grid, makeStyles, Paper, Typography } from '@material-ui/core';
import { AirplanemodeActive } from '@material-ui/icons';
import { Link, Redirect } from 'react-router-dom';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { getAllLeaves } from '../services/leaveService';
import LeaveData from '../types/LeaveData';
import ActionResult from '../types/ActionResult';
import { getUserId } from '../services/authService';

const useStyles = makeStyles((theme) => ({
  iconRoot: {
    backgroundColor: '#fdfdff',
  },
  root: {
    marginLeft: 'auto',
    marginRight: 'auto',
    padding: theme.spacing(3),
    width: '90%',
  },
  pageIcon: {
    display: 'inline-block',
    padding: theme.spacing(2),
    color: '#3c44b1',
  },
  pageTitle: {
    paddingLeft: theme.spacing(4),
    '& .MuiTypography-subtitle2': {
      opacity: '0.6',
    },
  },
}));

const LeavesView: React.FC = () => {
  const [leaves, setLeaves] = useState<LeaveData[] | undefined>([]);
  const classes = useStyles();

  useEffect(() => {
    const apiCall = async () => {
      const fetchedLeaves: ActionResult<LeaveData[]> = await getAllLeaves();
      if (fetchedLeaves.success) setLeaves(fetchedLeaves.data);
    };
    apiCall();
  }, []);

  const columns = [
    { field: 'firstName', headerName: 'First name', width: 300 },
    { field: 'lastName', headerName: 'Last name', width: 300 },
    { field: 'startDate', headerName: 'Start', width: 300 },
    { field: 'endDate', headerName: 'End', width: 300 },
    { field: 'paid', headerName: 'Paid', width: 200 },
    {
      field: 'user',
      headerName: 'Profile',
      width: 300,
      renderCell: ({ value }: GridCellParams) => (
        <strong>
          <IconButton
            component={Link}
            to={{ pathname: `/employee-profile`, state: { referer: '/leaves', userData: value } }}
          >
            <AccountCircleIcon />
          </IconButton>
        </strong>
      ),
    },
  ];

  const userId = getUserId();
  const rows: any = leaves
    ?.filter((a) => userId && a.user.id.toString() !== userId.toString())
    .map((a) => {
      return {
        id: a.id,
        firstName: a.user.personalData.firstname,
        lastName: a.user.personalData.lastname,
        startDate: a.startDate,
        endDate: a.endDate,
        paid: a.paid ? 'Yes' : 'No',
        user: a.user,
      };
    });

  return (
    <div className={classes.root}>
      <Grid container style={{ marginBottom: 25 }}>
        <Paper elevation={0} square className={classes.iconRoot}>
          <Card className={classes.pageIcon}>
            <AirplanemodeActive fontSize="large" />
          </Card>
        </Paper>
        <div className={classes.pageTitle}>
          <Typography variant="h6" component="div">
            Leaves
          </Typography>
          <Typography variant="subtitle2" component="div">
            Other people leaves
          </Typography>
        </div>
      </Grid>
      <Paper>
        <div style={{ width: '100%', overflowX: 'hidden', height: 700 }}>
          <DataGrid
            disableSelectionOnClick
            rows={rows}
            columns={columns}
            pageSize={20}
          />
        </div>
      </Paper>
    </div>
  );
};

export default LeavesView;
