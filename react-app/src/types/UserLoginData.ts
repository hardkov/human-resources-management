import UserType from './UserType';

interface UserLoginData {
  sub: string;
  username: string;
  userId: string;
  authorities: UserType;
}

export default UserLoginData;
