import React, { useEffect, useState } from 'react';
import { Button, makeStyles, Modal, Typography } from '@material-ui/core';
import { DataGrid, GridSelectionModelChangeParams, GridRowId, GridCellParams } from '@material-ui/data-grid';

import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';
import ActionResult from '../types/ActionResult';
import { downloadApplication, updateApplication } from '../services/applicationService';
import ApplicationStatus from '../types/ApplicationStatus';
import { getApplicationType } from '../helpers/application';
import ApplicationDetails from './ApplicationDetails';

const useStyles = makeStyles((theme) => ({
  root: {
    height: 1000,
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
  },
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    maxWidth: '80%',
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[2],
    padding: theme.spacing(2, 4, 3),
  },
  buttonGroup: {
    alignSelf: 'center',
    width: 'max-content',
  },
  button: {
    margin: theme.spacing(2),
    backgroundColor: theme.palette.secondary.light,
  },
  errorButton: {
    margin: theme.spacing(2),
    backgroundColor: theme.palette.error.main,
  },
}));

interface Props {
  initialApplications: (LeaveApplicationData | BonusApplicationData | DelegationApplicationData)[];
  disableActions: boolean;
}

const Applications: React.FC<Props> = ({ initialApplications, disableActions }: Props) => {
  const classes = useStyles();
  const [applications, setApplications] = useState<
    (LeaveApplicationData | BonusApplicationData | DelegationApplicationData)[]
  >(initialApplications);
  const [selectedApplications, setSelectesApplications] = useState<GridRowId[]>([]);
  const [details, setDetails] = useState<LeaveApplicationData | BonusApplicationData | DelegationApplicationData>();
  const [open, setOpen] = useState<boolean>(false);
  const [serverMessage, setServerMessage] = useState<string>('');
  const [success, setSuccess] = useState<boolean>(false);

  useEffect(() => {
    setApplications(initialApplications);
  }, [initialApplications]);

  const onSelectionModelChange = ({ selectionModel }: GridSelectionModelChangeParams) => {
    setSelectesApplications(selectionModel);
  };

  const openDetails = (rowId: GridRowId) => {
    const application = applications[rowId.valueOf() as number];
    setDetails(application);
    setOpen(true);
  };

  const downloadPdf = (rowId: GridRowId) => {
    const application = applications[rowId.valueOf() as number];
    const type = getApplicationType(application);

    downloadApplication(application.id, type);
  };

  const onApplicationAction = async (isAccept: boolean) => {
    const newStatus: ApplicationStatus = isAccept ? 'ACCEPTED' : 'REJECTED';
    const applicationsToManage = selectedApplications.map((rowId) => applications[rowId.valueOf() as number]);
    const promises: Promise<ActionResult>[] = [];

    applicationsToManage.forEach((application) => {
      const type = getApplicationType(application);
      const resultPromise = updateApplication(application.id, newStatus, type);
      promises.push(resultPromise);
    });

    let errorOccured = false;
    let errorMessage = '';
    promises.forEach(async (promise, idx) => {
      const { success: resultSuccess, errors } = await promise;
      if (!resultSuccess) {
        errorOccured = true;
        if (errors) [errorMessage] = errors;
      } else {
        applicationsToManage[idx].status = newStatus;
      }
    });

    setApplications([...applications]);

    if (errorOccured) {
      setSuccess(false);
      setServerMessage(errorMessage);
    } else {
      setSuccess(true);
      setServerMessage('Success');
    }
  };
  const columns = [
    { field: 'firstName', headerName: 'First name', flex: 1 },
    { field: 'lastName', headerName: 'Last name', flex: 1 },
    { field: 'type', headerName: 'Type', flex: 1 },
    { field: 'date', headerName: 'Date', flex: 1 },
    { field: 'status', headerName: 'Status', flex: 1 },
    {
      field: 'details',
      headerName: 'Details',
      flex: 0.5,
      renderCell: ({ rowIndex }: GridCellParams) => {
        return (
          <Button variant="contained" color="secondary" size="small" onClick={() => openDetails(rowIndex)}>
            Details
          </Button>
        );
      },
    },
    {
      field: 'pdf',
      headerName: 'Download',
      flex: 0.5,
      renderCell: ({ rowIndex }: GridCellParams) => {
        return (
          <Button variant="contained" color="primary" size="small" onClick={() => downloadPdf(rowIndex)}>
            PDF
          </Button>
        );
      },
    },
  ];

  const rows = applications?.map((application, idx) => ({
    id: idx,
    firstName: application.user.personalData.firstname,
    lastName: application.user.personalData.lastname,
    type: getApplicationType(application),
    date: application.date,
    status: application.status,
  })) as any[];

  return (
    <div className={classes.root}>
      <Typography align="center" color={success ? 'primary' : 'error'}>
        {serverMessage}
      </Typography>
      <Modal className={classes.modal} open={open} onClose={() => setOpen(false)}>
        <div className={classes.paper}>{details != null && <ApplicationDetails application={details} />}</div>
      </Modal>
      {!disableActions && (
        <div className={classes.buttonGroup}>
          <Button className={classes.button} variant="contained" onClick={() => onApplicationAction(true)}>
            Accept
          </Button>
          <Button className={classes.errorButton} variant="contained" onClick={() => onApplicationAction(false)}>
            Reject
          </Button>
        </div>
      )}
      <DataGrid
        rows={rows}
        columns={columns}
        checkboxSelection={!disableActions}
        onSelectionModelChange={onSelectionModelChange}
        selectionModel={selectedApplications}
        pageSize={20}
      />
    </div>
  );
};

export default Applications;
