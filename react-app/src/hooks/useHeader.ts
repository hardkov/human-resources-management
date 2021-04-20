import ButtonData from '../models/ButtonData';
import UserType from '../models/UserType';
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

  return [];
};

export default useHeader;
