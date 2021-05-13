import React from 'react';
import { useLocation } from 'react-router-dom';

import UserData from '../types/UserData';

import Profile from './Profile/Profile';

// location.state
interface LocationState {
  userData: UserData;
}

const EmployeeProfile: React.FC = () => {
  const location = useLocation<LocationState>();

  const { userData } = location.state;

  if (userData == null) return null;

  return <Profile userData={userData} />;
};

export default EmployeeProfile;
