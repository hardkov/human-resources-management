import React, { useEffect, useState } from 'react';
import { DataGrid } from '@material-ui/data-grid';
import { Paper, makeStyles, Card, Typography, Grid } from '@material-ui/core';

import { AirplanemodeActive } from '@material-ui/icons';
import { getLeavesByUser } from '../services/leaveService';
import LeaveData from '../types/LeaveData';
import ActionResult from '../types/ActionResult';
import { getUserId } from '../services/authService';

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: 'auto',
    marginRight: 'auto',
    padding: theme.spacing(3),
    width: '90%',
  },
  iconRoot: {
    backgroundColor: '#fdfdff',
  },
  pageHeader: {
    padding: theme.spacing(4),
    display: 'flex',
    marginBottom: theme.spacing(3),
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

const MyLeavesView: React.FC = () => {
  const [leaves, setLeaves] = useState<LeaveData[] | undefined>([]);
  const classes = useStyles();

  useEffect(() => {
    const apiCall = async () => {
      const userId: number | undefined = getUserId();
      if (userId) {
        const fetchedLeaves: ActionResult<LeaveData[]> = await getLeavesByUser(userId.toString());
        if (fetchedLeaves.success) setLeaves(fetchedLeaves.data);
      }
    };
    apiCall();
  }, []);

  const columns = [
    { field: 'startDate', headerName: 'Start', width: 300 },
    { field: 'endDate', headerName: 'End', width: 300 },
    { field: 'paid', headerName: 'Paid', width: 300 },
  ];

  const rows: any = leaves?.map((a) => {
    return {
      id: a.id,
      startDate: a.startDate,
      endDate: a.endDate,
      paid: a.paid ? 'Yes' : 'No',
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
            My leaves
          </Typography>
        </div>
      </Grid>
      <Paper>
        <div style={{ width: '100%', overflowX: 'hidden', height: 700 }}>
          <DataGrid rows={rows} columns={columns} pageSize={20} />
        </div>
      </Paper>
    </div>
  );
};

export default MyLeavesView;
