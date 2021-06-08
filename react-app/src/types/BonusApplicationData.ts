import ApplicationStatus from './ApplicationStatus';
import UserData from './UserData';

interface BonusApplicationData {
  id: number;
  content: string;
  user: UserData;
  place: string;
  date: string;
  status: ApplicationStatus;
  money: string;
}

export default BonusApplicationData;
