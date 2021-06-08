import React, { useState, useEffect } from 'react';

import { Paper, makeStyles, Card, Typography, Grid } from '@material-ui/core';
import DescriptionIcon from '@material-ui/icons/Description';

import { getLeaveApplications, getDelegationApplications, getBonusApplications } from '../services/applicationService';

import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';
import Applications from './Applications';

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

const EmployeeAplications: React.FC = () => {
  const classes = useStyles();
  const [leaveApplications, setLeaveApplications] = useState<LeaveApplicationData[]>([]);
  const [bonusApplications, setBonusApplications] = useState<BonusApplicationData[]>([]);
  const [delegationApplications, setDelegationApplications] = useState<DelegationApplicationData[]>([]);

  useEffect(() => {
    const fetchLeave = async () => {
      const { success, data } = await getLeaveApplications();
      if (success && data) setLeaveApplications(data);
    };

    const fetchBonus = async () => {
      const { success, data } = await getBonusApplications();
      if (success && data) setBonusApplications(data);
    };

    const fetchDelegation = async () => {
      const { success, data } = await getDelegationApplications();
      if (success && data) setDelegationApplications(data);
    };

    fetchLeave();
    fetchBonus();
    fetchDelegation();
  }, []);

  return (
    <div className={classes.root}>
      <Grid container style={{ marginBottom: 25 }}>
        <Paper elevation={0} square className={classes.iconRoot}>
          <Card className={classes.pageIcon}>
            <DescriptionIcon fontSize="large" />
          </Card>
        </Paper>
        <div className={classes.pageTitle}>
          <Typography variant="h6" component="div">
            Applications
          </Typography>
          <Typography variant="subtitle2" component="div">
            Other people applications
          </Typography>
        </div>
      </Grid>
      <Paper>
        <div style={{ width: '100%', overflowX: 'hidden', height: 700 }}>
          <Applications
            initialApplications={[...leaveApplications, ...bonusApplications, ...delegationApplications]}
            disableActions={false}
          />
        </div>
      </Paper>
    </div>
  );
};

export default EmployeeAplications;
