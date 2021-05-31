import React, { useEffect, useState } from 'react';
import { DataGrid, GridSelectionModelChangeParams, GridCellModeChangeParams } from '@material-ui/data-grid';
import ActionResult from '../types/ActionResult';
import LeaveApplicationData from '../types/LeaveApplicationData';
import { getLeaveApplications } from '../services/applicationService';

// onCellClick
// onSelectionModelChange
const Applications: React.FC = () => {
  const [leaveApplications, setLeaveApplications] = useState<LeaveApplicationData[] | undefined>([]);
  // const [selectedLeaveApplications, setSelectedLeaveApplications] = useState;

  const onSelectionModelChange = (args: GridSelectionModelChangeParams) => {
    console.log('onSelectionModelChange');
    console.log(args.selectionModel);
  };

  useEffect(() => {
    const apiCall = async () => {
      const result: ActionResult<LeaveApplicationData[]> = await getLeaveApplications();
      if (result.success) setLeaveApplications(result.data);
    };

    apiCall();
  }, []);

  const columns = [
    { field: 'firstName', headerName: 'First name', width: 300 },
    { field: 'lastName', headerName: 'Last name', width: 300 },
    { field: 'type', headerName: 'Type', width: 300 },
    { field: 'date', headerName: 'Date', width: 300 },
    { field: 'status', headerName: 'Status', width: 300 },
  ];

  const rows = leaveApplications?.map((application) => ({
    id: application.id,
    firstName: application.user.personalData.firstname,
    lastName: application.user.personalData.lastname,
    type: 'Leave application',
    date: application.date,
    status: application.status,
  })) as any[];

  return (
    <div style={{ height: 1000, width: '100%' }}>
      <DataGrid
        rows={rows}
        columns={columns}
        checkboxSelection
        onSelectionModelChange={onSelectionModelChange}
        pageSize={20}
      />
    </div>
  );
};

export default Applications;
