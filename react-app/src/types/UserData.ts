import PersonalData from './PersonalData';

interface UserData {
  id: number | null;
  personalData: PersonalData;
  position: string;
}

export default UserData;
