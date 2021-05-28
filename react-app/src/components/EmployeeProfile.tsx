import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import { getPermission } from '../services/userService';
import UserData from '../types/UserData';
import Permission from './Permission';
import Profile from './Profile/Profile';
import PermissionData from '../types/PermissionData';
import { getUserId } from '../services/authService';

interface LocationState {
  userData: UserData;
  referer?: string;
}

const EmployeeProfile: React.FC = () => {
  const location = useLocation<LocationState>();
  const [permission, setPermission] = useState<PermissionData>();
  const [currentUserPermission, setCurrentUserPermission] = useState<PermissionData>();
  const { userData, referer } = location.state;

  useEffect(() => {
    const fetchPermissions = async () => {
      const { success, data } = await getPermission(userData.id);
      if (success) setPermission(data);
    };

    const fetchCurrentUserPermissions = async () => {
      const userId = getUserId();

      if (userId == null) return;

      const { success, data } = await getPermission(userId);
      if (success) setCurrentUserPermission(data);
    };

    fetchPermissions();
    fetchCurrentUserPermissions();
  }, [userData.id]);

  if (userData == null) return null;

  return (
    <>
      <Profile userData={userData} referer={referer} />
      {permission != null && currentUserPermission != null && (
        <Permission permission={permission} currentUserPermission={currentUserPermission} />
      )}
    </>
  );
};

export default EmployeeProfile;
