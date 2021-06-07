import { Grid, makeStyles, Paper, Typography } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { DataGrid } from '@material-ui/data-grid';
import DateFnsAdapter from '@date-io/date-fns';
import ActionResult from '../types/ActionResult';
import { getContractsByUser } from '../services/contractService';

import ContractData from '../types/ContractData';
import { getUserId } from '../services/authService';

const useStyles = makeStyles(() => ({
  root: {
    marginLeft: 'auto',
    marginRight: 'auto',
    width: '90%',
  },
  pageContent: {
    height: 700,
    width: '100%',
    overflowX: 'hidden',
  },
  infoTitle: {
    padding: '20px',
  },
  infoPaper: {
    padding: '20px',
    backgroundColor: '#f2f2f2',
  },
}));

const EarningsView: React.FC = () => {
  const classes = useStyles();
  const [contracts, setContracts] = useState<ContractData[] | undefined>([]);

  useEffect(() => {
    const apiCall = async () => {
      const userId = getUserId();
      if (userId) {
        const fetchedContracts: ActionResult<ContractData[]> = await getContractsByUser(userId.toString());
        if (fetchedContracts.success) setContracts(fetchedContracts.data);
      }
    };
    apiCall();
  }, []);

  const columns = [
    { field: 'startDate', headerName: 'Start date', width: 120 },
    { field: 'endDate', headerName: 'End date', width: 120 },
    { field: 'contract', headerName: 'Contract Type', width: 150 },
    { field: 'baseSalary', headerName: 'Base salary', width: 140 },
  ];

  const fnsAdapter = new DateFnsAdapter();
  const activeContract = contracts?.find((c) => {
    const { startDate, endDate } = c;
    const startDateParsed = fnsAdapter.parse(startDate, 'yyyy-MM-dd');
    const endDateParsed = fnsAdapter.parse(endDate, 'yyyy-MM-dd');
    return startDateParsed.getTime() <= Date.now() && Date.now() <= endDateParsed.getTime();
  });

  const rows: any = contracts
    ?.filter(
      (c) => activeContract === undefined || (activeContract && activeContract.id.toString() !== c.id.toString()),
    )
    ?.map((c) => {
      return {
        id: c.id,
        startDate: c.startDate,
        endDate: c.endDate,
        contract: c.contractType,
        baseSalary: c.baseSalary,
      };
    });

  return (
    <div className={classes.root}>
      <Grid container spacing={2}>
        <Grid item md={6} sm={12} xs={12} style={{ padding: '10px' }} spacing={2}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Typography variant="h4" className={classes.infoTitle}>
                Active contract
              </Typography>
            </Grid>
            {activeContract && (
              <>
                <Grid item xs={12}>
                  <Paper className={classes.infoPaper}>
                    <Typography variant="h5" style={{ fontWeight: 600 }}>
                      Base salary
                    </Typography>
                    <Typography variant="h6">{activeContract.baseSalary}</Typography>
                  </Paper>
                </Grid>
                <Grid item xs={12}>
                  <Paper className={classes.infoPaper}>
                    <Typography variant="h5" style={{ fontWeight: 600 }}>
                      Contract type
                    </Typography>
                    <Typography variant="h6">{activeContract.contractType}</Typography>
                  </Paper>
                </Grid>
                <Grid item xs={12}>
                  <Paper className={classes.infoPaper}>
                    <Typography variant="h5" style={{ fontWeight: 600 }}>
                      Start date
                    </Typography>
                    <Typography variant="h6">{activeContract.startDate}</Typography>
                  </Paper>
                </Grid>
                <Grid item xs={12}>
                  <Paper className={classes.infoPaper}>
                    <Typography variant="h5" style={{ fontWeight: 600 }}>
                      End date
                    </Typography>
                    <Typography variant="h6">{activeContract.endDate}</Typography>
                  </Paper>
                </Grid>
              </>
            )}
          </Grid>
        </Grid>
        <Grid item md={6} sm={12} xs={12} style={{ padding: '10px' }}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Typography variant="h4" className={classes.infoTitle}>
                Contract history
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <Paper>
                <div className={classes.pageContent}>
                  <DataGrid
                    disableSelectionOnClick
                    rowsPerPageOptions={[10, 20, 50]}
                    pagination
                    rows={rows}
                    columns={columns}
                  />
                </div>
              </Paper>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
};

export default EarningsView;
