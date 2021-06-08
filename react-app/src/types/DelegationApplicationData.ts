import ApplicationStatus from './ApplicationStatus';
import UserData from './UserData';

interface DelegationApplicationData {
  id: number;
  content: string;
  user: UserData;
  place: string;
  date: string;
  status: ApplicationStatus;
  startDate: string;
  endDate: string;
  destination: string;
}

export default DelegationApplicationData;
