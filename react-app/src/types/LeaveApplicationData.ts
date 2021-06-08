import ApplicationStatus from './ApplicationStatus';
import UserData from './UserData';

interface LeaveApplicationData {
  id: number;
  content: string;
  user: UserData;
  place: string;
  date: string;
  status: ApplicationStatus;
  startDate: string;
  endDate: string;
  paid: boolean;
}

export default LeaveApplicationData;
