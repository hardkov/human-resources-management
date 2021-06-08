import ApplicationType from '../types/ApplicationType';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';
import LeaveApplicationData from '../types/LeaveApplicationData';

const getApplicationType = (
  application: LeaveApplicationData | BonusApplicationData | DelegationApplicationData,
): ApplicationType => {
  if ((application as any).money != null) return 'BONUS';
  if ((application as any).destination != null) return 'DELEGATION';
  return 'LEAVE';
};

export { getApplicationType };
