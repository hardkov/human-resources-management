import { Typography } from '@material-ui/core';
import React from 'react';

import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';

import { getApplicationType } from '../helpers/application';

interface Props {
  application: LeaveApplicationData | BonusApplicationData | DelegationApplicationData;
}

const ApplicationDetails: React.FC<Props> = ({ application }: Props) => {
  const type = getApplicationType(application);
  const baseItems = [
    {
      name: 'Content',
      content: application.content,
    },
    {
      name: 'Place',
      content: application.place,
    },
    {
      name: 'Date',
      content: application.date,
    },
  ];

  const leaveItems = [
    {
      name: 'Start date',
      content: (application as LeaveApplicationData).startDate,
    },
    {
      name: 'End date',
      content: (application as LeaveApplicationData).endDate,
    },
    {
      name: 'Paid',
      content: (application as LeaveApplicationData).paid ? 'Yes' : 'No',
    },
  ];

  const bonusItems = [
    {
      name: 'Amount',
      content: (application as BonusApplicationData).money,
    },
  ];

  const delegationItems = [
    {
      name: 'Start date',
      content: (application as DelegationApplicationData).startDate,
    },
    {
      name: 'End date',
      content: (application as DelegationApplicationData).endDate,
    },
    {
      name: 'Destination',
      content: (application as DelegationApplicationData).destination,
    },
  ];

  let items = [...baseItems, ...leaveItems];
  if (type === 'DELEGATION') items = [...baseItems, ...delegationItems];
  if (type === 'BONUS') items = [...baseItems, ...bonusItems];
  return (
    <>
      {items.map(({ name, content }) => (
        <>
          <strong>{name}</strong>
          <Typography>{content}</Typography>
        </>
      ))}
    </>
  );
};

export default ApplicationDetails;
