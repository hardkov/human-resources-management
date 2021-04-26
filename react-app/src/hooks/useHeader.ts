import ButtonData from '../types/ButtonData';
import UserType from '../types/UserType';
import { getUserType } from '../services/authService';

const useHeader = (): ButtonData[] => {
  const currentUser: UserType = getUserType();

  if (currentUser === 'ADMIN') {
    return [
      {
        text: 'Logout',
        link: '/',
      },
    ];
  }

  if (currentUser === 'EMPLOYEE') {
    return [
      {
        text: 'Logout',
        link: '/',
      },
      {
        text: 'My Profile',
        link: '/',
      },
    ];
  }

  if (currentUser === 'SUPERVISOR') {
    return [
      {
        text: 'Logout',
        link: '/',
      },
      {
        text: 'My Profile',
        link: '/',
      },
    ];
  }

  return [];
};

export default useHeader;
