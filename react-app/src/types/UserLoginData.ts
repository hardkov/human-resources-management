import UserType from './UserType';

interface UserLoginData {
  sub: string;
  username: string;
  userId: number;
  authorities: UserType;
}

export default UserLoginData;
