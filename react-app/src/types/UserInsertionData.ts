import PersonalData from "./PersonalData";
import UserType from "./UserType";

interface UserInsertionData {
  personalData: PersonalData,
  position: string,
  username: string,
  password: string,
  role: UserType
}

export default UserInsertionData;
