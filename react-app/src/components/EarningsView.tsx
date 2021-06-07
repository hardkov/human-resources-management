import { IconButton, makeStyles, Paper } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import MonetizationOnIcon from '@material-ui/icons/MonetizationOn';
import { DataGrid, GridCellParams } from '@material-ui/data-grid';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { Link } from 'react-router-dom';
import EarningsHeader from './EarningsHeader';
import ActionResult from '../types/ActionResult';
import { getAllContracts } from '../services/contractService';

import ContractData from '../types/ContractData';
import { getUserId } from '../services/authService';

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: 'auto',
    marginRight: 'auto',
    padding: theme.spacing(3),
    width: '90%',
  },
  pageContent: {
    height: 700,
    width: '100%',
    overflowX: 'hidden',
  },
}));

const EarningsView: React.FC = () => {
  const classes = useStyles();
  const [contracts, setContracts] = useState<ContractData[] | undefined>([]);

  useEffect(() => {
    const apiCall = async () => {
      const fetchedContracts: ActionResult<ContractData[]> = await getAllContracts();
      if (fetchedContracts.success) setContracts(fetchedContracts.data);
    };
    apiCall();
  }, []);

  const columns = [
    { field: 'firstName', headerName: 'First name', width: 280 },
    { field: 'lastName', headerName: 'Last name', width: 280 },
    { field: 'startDate', headerName: 'Start date', width: 200 },
    { field: 'endDate', headerName: 'End date', width: 200 },
    { field: 'contract', headerName: 'Contract Type', width: 200 },
    { field: 'baseSalary', headerName: 'Base salary', width: 200 },
    {
      field: 'user',
      headerName: 'Profile',
      width: 300,
      renderCell: ({ value }: GridCellParams) => (
        <strong>
          <IconButton
            component={Link}
            to={{ pathname: `/employee-profile`, state: { referer: '/earnings', userData: value } }}
          >
            <AccountCircleIcon />
          </IconButton>
        </strong>
      ),
    },
  ];

  const userId = getUserId();
  const rows: any = contracts
    ?.filter((c) => userId && c.user.id.toString() !== userId.toString())
    .map((c) => {
      return {
        id: c.id,
        firstName: c.user.personalData.firstname,
        lastName: c.user.personalData.lastname,
        startDate: c.startDate,
        endDate: c.endDate,
        contract: c.contractType,
        baseSalary: c.baseSalary,
        user: c.user,
      };
    });

  return (
    <div className={classes.root}>
      <EarningsHeader
        title="Earnings"
        subTitle="Subordinates' contracts"
        icon={<MonetizationOnIcon fontSize="large" />}
      />
      <Paper>
        <div className={classes.pageContent}>
          <DataGrid disableSelectionOnClick pagination rows={rows} columns={columns} />
        </div>
      </Paper>
    </div>
  );
};

export default EarningsView;
