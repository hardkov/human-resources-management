import { makeStyles, Paper } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import MonetizationOnIcon from '@material-ui/icons/MonetizationOn';
import { DataGrid, GridPageChangeParams } from '@material-ui/data-grid';
import EarningsHeader from './EarningsHeader';
import ActionResult from '../types/ActionResult';
import { getAllContracts } from '../services/contractService';

import ContractData from '../types/ContractData';

const useStyles = makeStyles((theme) => ({
  pageContent: {
    margin: theme.spacing(5),
    padding: theme.spacing(3),
  },
}));

const EarningsView: React.FC = () => {
  const classes = useStyles();
  const [contracts, setContracts] = useState<ContractData[] | undefined>([]);
  const [pageSize, setPageSize] = React.useState<number>(20);

  const handlePageSizeChange = (params: GridPageChangeParams) => {
    setPageSize(params.pageSize);
  };

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
    { field: 'startDate', headerName: 'Start date', width: 280 },
    { field: 'endDate', headerName: 'End date', width: 280 },
    { field: 'contract', headerName: 'Contract Type', width: 280 },
    { field: 'baseSalary', headerName: 'Base salary', width: 280 },
  ];

  const rows: any = [];
  contracts?.forEach((c) =>
    rows.push({
      id: c.id,
      firstName: c.user.personalData.firstname,
      lastName: c.user.personalData.lastname,
      startDate: c.startDate,
      endDate: c.endDate,
      contract: c.contractType,
      baseSalary: c.baseSalary,
    }),
  );

  return (
    <>
      <EarningsHeader
        title="Earnings View"
        subTitle="Subordinates' contracts"
        icon={<MonetizationOnIcon fontSize="large" />}
      />
      <Paper className={classes.pageContent}>
        <div style={{ height: 800, width: '100%', overflowX: 'hidden' }}>
          <DataGrid
            pageSize={pageSize}
            onPageSizeChange={handlePageSizeChange}
            rowsPerPageOptions={[10, 20, 50]}
            pagination
            rows={rows}
            columns={columns}
          />
        </div>
      </Paper>
    </>
  );
};

export default EarningsView;
