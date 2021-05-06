import UserData from './UserData';

interface LeaveData {
  id: number;
  user: UserData;
  position: string;
  startDate: string;
  endDate: string;
  paid: boolean;
  thumbnail: string[] | null;
}

export default LeaveData;
