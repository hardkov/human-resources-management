import UserType from '../models/UserType';
import HomeMenuItemData from '../models/HomeMenuItemData';
import { getUserType } from '../services/authService';
import applicationImg from '../assets/applications.png';
import employeesImg from '../assets/employees.jpg';
import logsImg from '../assets/logs.png';

const useHomeMenu = (): HomeMenuItemData[] => {
  const currentUser: UserType = getUserType();

  if (currentUser === 'ADMIN') {
    return [
      {
        img: logsImg,
        title: 'Logs',
        description: 'See all the data modification done by system users.',
        link: '/',
      },
      {
        img: employeesImg,
        title: 'Employees',
        description: 'See all the employees. Manage their permission and get insight into user data details.',
        link: '/',
      },
      {
        img: applicationImg,
        title: 'Applications',
        description: 'See all applications from the users and accept or reject them.',
        link: '/',
      },
    ];
  }
  return [];
};

export default useHomeMenu;
