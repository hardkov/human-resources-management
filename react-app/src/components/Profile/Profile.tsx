import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';

import UserData from '../../types/UserData';
import { getUserId } from '../../services/authService';
import { getUser, updateUser } from '../../services/userService';

import ProfileForm from './ProfileForm';

interface Props {
  userData?: UserData;
}

type FormValues = {
  firstname: string;
  lastname: string;
  email: string;
  phoneNumber: string;
  address: string;
  birthdate: string;
};

const Profile: React.FC<Props> = ({ userData }: Props) => {
  const [data, setData] = useState<UserData>();
  const [changeMode, setChangeMode] = useState<boolean>(false);
  const [serverError, setServerError] = useState<string>('');

  const { register, handleSubmit, reset } = useForm<FormValues>({
    defaultValues: {
      firstname: data?.personalData.firstname,
      lastname: data?.personalData.lastname,
      email: data?.personalData.email,
      phoneNumber: data?.personalData.phoneNumber,
      address: data?.personalData.address,
      birthdate: data?.personalData.birthdate,
    },
  });

  useEffect(() => {
    const fetchUserData = async () => {
      const currentUserId = getUserId();

      if (currentUserId == null) return;

      const result = await getUser(currentUserId);

      if (result.success) setData(result.data);
    };

    if (userData == null) fetchUserData();
    else setData(userData);
  }, [userData]);

  useEffect(() => {
    reset({
      firstname: data?.personalData.firstname,
      lastname: data?.personalData.lastname,
      email: data?.personalData.email,
      phoneNumber: data?.personalData.phoneNumber,
      address: data?.personalData.address,
      birthdate: data?.personalData.birthdate,
    });
    setServerError('');
  }, [data, changeMode, reset]);

  const handleSubmitCallback = async (formData: any) => {
    if (data == null) return;

    const personalDataCopy = { ...data.personalData, ...formData };
    const userDataCopy = { ...data, personalData: personalDataCopy };
    const result = await updateUser(userDataCopy);

    if (result.success) {
      setData(userDataCopy);
      setChangeMode(false);
      return;
    }

    if (result.errors != null) setServerError(result.errors[0]);
  };

  return (
    <ProfileForm
      setChangeMode={setChangeMode}
      changeMode={changeMode}
      register={register}
      handleSubmit={handleSubmit(handleSubmitCallback)}
      serverError={serverError}
    />
  );
};

export default Profile;
