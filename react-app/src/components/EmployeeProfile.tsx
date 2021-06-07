import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import { getAllUsers, getPermission } from '../services/userService';
import UserData from '../types/UserData';
import Permission from './Permission';
import Profile from './Profile/Profile';
import PermissionData from '../types/PermissionData';
import { getUserId, getUserType } from '../services/authService';

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
      const currentUser = getUserType();
      const userId = getUserId();

      if (userId == null) return;

      const { success: successPermission, data: dataPermission } = await getPermission(userId);
      if (!successPermission || !dataPermission) return;

      if (currentUser === 'ADMIN') {
        const { success: successUsers, data: dataUsers } = await getAllUsers();

        if (successUsers && dataUsers) {
          const allUsersIds = dataUsers.map((value) => value.id);

          dataPermission.read = allUsersIds;
          dataPermission.write = [...allUsersIds];
        }
      }

      setCurrentUserPermission(dataPermission);
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
