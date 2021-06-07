import UserType from '../types/UserType';
import HomeMenuItemData from '../types/HomeMenuItemData';
import { getUserType } from '../services/authService';

import applicationImg from '../assets/applications.jpg';
import employeesImg from '../assets/employees.jpg';
import logsImg from '../assets/logs.png';
import myApplicationsImg from '../assets/myApplications.jpg';
import createApplicationsImg from '../assets/createApplication.png';
import earningsImg from '../assets/earnings.jpg';
import myEarningsImg from '../assets/myEarnings.jpeg';
import leavesImg from '../assets/leaves.jpg';
import myLeavesImg from '../assets/myLeaves.jpg';

const logsInfo = {
  img: logsImg,
  title: 'Logs',
  description: 'See all the data modification done by system users.',
  link: '/',
};

const employeesInfo = {
  img: employeesImg,
  title: 'Employees',
  description: 'List of your subordinates. Manage their permission and get insight into user data details.',
  link: '/employees',
};

const applicationInfo = {
  img: applicationImg,
  title: 'Applications',
  description: 'All applications from your subordinates.',
  link: '/',
};

const myApplicationInfo = {
  img: myApplicationsImg,
  title: 'My Applications',
  description: 'Applications that you have submitted.',
  link: '/',
};

const myEarningsInfo = {
  img: myEarningsImg,
  title: 'My Earnings',
  description: 'Aggregated data about your earnings.',
  link: '/my-earnings',
};

const myLeavesInfo = {
  img: myLeavesImg,
  title: 'My Leaves',
  description: 'See all the leaves during your contract.',
  link: '/my-leaves',
};

const createApplicationInfo = {
  img: createApplicationsImg,
  title: 'Create Application',
  description: 'Create and submit an application.',
  link: '/create-application',
};

const leavesInfo = {
  img: leavesImg,
  title: 'Leaves',
  description: 'See all the leaves of your subordinates.',
  link: '/leaves',
};

const earningsInfo = {
  img: earningsImg,
  title: 'Earnings',
  description: 'See all the earnings of your subordinates.',
  link: '/earnings',
};

const useHomeMenu = (): HomeMenuItemData[] => {
  const currentUser: UserType | undefined = getUserType();

  if (currentUser === 'ADMIN') {
    return [logsInfo, employeesInfo, applicationInfo];
  }
  if (currentUser === 'EMPLOYEE') {
    return [myApplicationInfo, myLeavesInfo, myEarningsInfo, createApplicationInfo];
  }
  if (currentUser === 'SUPERVISOR') {
    return [
      myApplicationInfo,
      myLeavesInfo,
      myEarningsInfo,
      applicationInfo,
      leavesInfo,
      earningsInfo,
      employeesInfo,
      createApplicationInfo,
    ];
  }

  return [];
};

export default useHomeMenu;
