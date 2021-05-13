import ButtonData from '../types/ButtonData';
import UserType from '../types/UserType';
import { getUserName, getUserType } from '../services/authService';

const useHeader = (): [string | undefined, ButtonData[]] => {
  const userType: UserType | undefined = getUserType();
  const username = getUserName();
  let buttons: ButtonData[] = [];

  if (userType === 'ADMIN') {
    buttons = [
      {
        text: 'Logout',
        link: '/logout',
      },
    ];
  } else if (userType === 'EMPLOYEE') {
    buttons = [
      {
        text: 'Logout',
        link: '/logout',
      },
      {
        text: 'My Profile',
        link: '/profile',
      },
    ];
  } else if (userType === 'SUPERVISOR') {
    buttons = [
      {
        text: 'Logout',
        link: '/logout',
      },
      {
        text: 'My Profile',
        link: '/profile',
      },
    ];
  }

  return [username, buttons];
};

export default useHeader;
