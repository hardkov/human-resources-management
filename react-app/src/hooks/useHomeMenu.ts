import UserType from '../types/UserType';
import HomeMenuItemData from '../types/HomeMenuItemData';
import { getUserType } from '../services/authService';

import applicationImg from '../assets/applications.png';
import employeesImg from '../assets/employees.jpg';
import logsImg from '../assets/logs.png';
import myapplicationsImg from '../assets/myapplications.jpg';
import earningsImg from '../assets/earnings.jpg';
import leavesImg from '../assets/leaves.jpg';

const useHomeMenu = (): HomeMenuItemData[] => {
  const currentUser: UserType | undefined = getUserType();

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
        description: 'List of employees. Manage their permission and get insight into user data details.',
        link: '/',
      },
      {
        img: applicationImg,
        title: 'Applications',
        description: 'All applications from the users.',
        link: '/',
      },
    ];
  }

  if (currentUser === 'EMPLOYEE') {
    return [
      {
        img: myapplicationsImg,
        title: 'My Applications',
        description: 'Applications that you have submitted.',
        link: '/',
      },
      {
        img: leavesImg,
        title: 'My Leaves',
        description: 'See all the leaves during your contract.',
        link: '/',
      },
      {
        img: earningsImg,
        title: 'My Earnings',
        description: 'Aggregated data about your earnings.',
        link: '/',
      },
      {
        img: applicationImg,
        title: 'Create Application',
        description: 'Create and submit an applications.',
        link: '/',
      },
    ];
  }

  if (currentUser === 'SUPERVISOR') {
    return [
      {
        img: myapplicationsImg,
        title: 'My Applications',
        description: 'Applications that you have submitted.',
        link: '/',
      },
      {
        img: leavesImg,
        title: 'My Leaves',
        description: 'See all the leaves during your contract.',
        link: '/leaves',
      },
      {
        img: earningsImg,
        title: 'My Earnings',
        description: 'Aggregated data about your earnings.',
        link: '/',
      },
      {
        img: applicationImg,
        title: 'Create Application',
        description: 'Create and submit an applications.',
        link: '/',
      },
      {
        img: employeesImg,
        title: 'Employees',
        description: 'List of employees. Manage their permission and get insight into user data details.',
        link: '/',
      },
      {
        img: applicationImg,
        title: 'Applications',
        description: 'All applications from the users.',
        link: '/',
      },
    ];
  }

  return [];
};

export default useHomeMenu;
