import React, { useEffect, useState } from 'react';
import { DataGrid } from '@material-ui/data-grid';
import { getAllLeaves } from '../services/LeaveService';
import LeaveData from '../types/LeaveData';
import ActionResult from '../types/ActionResult';

const LeavesView: React.FC = () => {
  const [leaves, setLeaves] = useState<LeaveData[] | undefined>([]);

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
    { field: 'paid', headerName: 'Paid', width: 300 },
  ];

  const rows: any = [];

  leaves?.forEach((a) =>
    rows.push({
      id: a.id,
      firstName: a.user.personalData.firstname,
      lastName: a.user.personalData.lastname,
      startDate: a.startDate,
      endDate: a.endDate,
      paid: a.paid ? 'yes' : 'no',
    }),
  );

  return (
    <div style={{ height: 1000, width: '100%' }}>
      <DataGrid rows={rows} columns={columns} pageSize={20} />
    </div>
  );
};

export default LeavesView;
