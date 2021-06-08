import React, { useState, useEffect } from 'react';

import {
  getMyLeaveApplications,
  getMyBonusApplications,
  getMyDelegationApplications,
} from '../services/applicationService';

import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';
import Applications from './Applications';

const MyApplications: React.FC = () => {
  const [leaveApplications, setLeaveApplications] = useState<LeaveApplicationData[]>([]);
  const [bonusApplications, setBonusApplications] = useState<BonusApplicationData[]>([]);
  const [delegationApplications, setDelegationApplications] = useState<DelegationApplicationData[]>([]);

  useEffect(() => {
    const fetchLeave = async () => {
      const { success, data } = await getMyLeaveApplications();
      if (success && data) setLeaveApplications(data);
    };

    const fetchBonus = async () => {
      const { success, data } = await getMyBonusApplications();
      if (success && data) setBonusApplications(data);
    };

    const fetchDelegation = async () => {
      const { success, data } = await getMyDelegationApplications();
      if (success && data) setDelegationApplications(data);
    };

    fetchLeave();
    fetchBonus();
    fetchDelegation();
  }, []);

  return (
    <Applications
      initialApplications={[...leaveApplications, ...bonusApplications, ...delegationApplications]}
      disableActions
    />
  );
};

export default MyApplications;
