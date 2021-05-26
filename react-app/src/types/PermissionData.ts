import UserData from './UserData';

interface PermissionData {
  id: number;
  user: UserData;
  write: number[];
  read: number[];
  add: boolean;
}

export default PermissionData;
