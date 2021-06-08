import React, { useState, useEffect } from 'react';

import { getLeaveApplications, getDelegationApplications, getBonusApplications } from '../services/applicationService';

import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';
import Applications from './Applications';

const EmployeeAplications: React.FC = () => {
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
    <Applications
      initialApplications={[...leaveApplications, ...bonusApplications, ...delegationApplications]}
      disableActions={false}
    />
  );
};

export default EmployeeAplications;
