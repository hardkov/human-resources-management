import UserData from "./UserData";

interface UserInsertionData extends UserData {
  username: string,
  password: string,
  role: string
}

export default UserInsertionData;
