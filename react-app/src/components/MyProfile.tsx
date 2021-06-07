import React, { useEffect, useState } from 'react';

import UserData from '../types/UserData';
import { getUserId } from '../services/authService';
import { getUser } from '../services/userService';

import Profile from './Profile/Profile';

const MyProfile: React.FC = () => {
  const [data, setData] = useState<UserData>();

  useEffect(() => {
    const fetchUserData = async () => {
      const currentUserId = getUserId();

      if (currentUserId == null) return;

      const result = await getUser(currentUserId);

      if (result.success) setData(result.data);
    };

    fetchUserData();
  }, []);

  if (data == null) return null;

  return <Profile userData={data} disabled />;
};

export default MyProfile;
