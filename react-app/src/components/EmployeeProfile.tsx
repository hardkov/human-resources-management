import React from 'react';
import { useLocation } from 'react-router-dom';

import UserData from '../types/UserData';

import Profile from './Profile/Profile';

interface LocationState {
  userData: UserData;
  referer?: string;
}

const EmployeeProfile: React.FC = () => {
  const location = useLocation<LocationState>();

  const { userData, referer } = location.state;

  if (userData == null) return null;

  return <Profile userData={userData} referer={referer} />;
};

export default EmployeeProfile;
