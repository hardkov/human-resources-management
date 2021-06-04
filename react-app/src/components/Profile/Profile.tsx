import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';

import UserData from '../../types/UserData';
import { updateUser } from '../../services/userService';

import ProfileForm from './ProfileForm';

interface Props {
  userData: UserData;
  disabled?: boolean;
  referer?: string;
}

type FormValues = {
  firstname: string;
  lastname: string;
  email: string;
  phoneNumber: string;
  address: string;
  birthdate: string;
};

const Profile: React.FC<Props> = ({ userData, referer, disabled }: Props) => {
  const [changeMode, setChangeMode] = useState<boolean>(false);
  const [serverMessage, setServerMessage] = useState<string>('');
  const [success, setSuccess] = useState<boolean>(false);
  const [data, setData] = useState<UserData>(userData);

  const { register, handleSubmit, reset } = useForm<FormValues>({
    defaultValues: {
      firstname: data.personalData.firstname,
      lastname: data.personalData.lastname,
      email: data.personalData.email,
      phoneNumber: data.personalData.phoneNumber,
      address: data.personalData.address,
      birthdate: data.personalData.birthdate,
    },
  });

  useEffect(() => {
    reset({
      firstname: data.personalData.firstname,
      lastname: data.personalData.lastname,
      email: data.personalData.email,
      phoneNumber: data.personalData.phoneNumber,
      address: data.personalData.address,
      birthdate: data.personalData.birthdate,
    });
  }, [data, changeMode, reset]);

  const handleSubmitCallback = async (formData: any) => {
    if (userData == null) return;

    const personalDataCopy = { ...userData.personalData, ...formData };
    const userDataCopy = { ...userData, personalData: personalDataCopy };
    const result = await updateUser(userDataCopy);

    if (result.success) {
      setSuccess(true);
      setData(userDataCopy);
      setChangeMode(false);
      setServerMessage('Success');
      return;
    }

    if (result.errors != null) {
      setServerMessage(result.errors[0]);
      setSuccess(false);
    }
  };

  return (
    <ProfileForm
      setChangeMode={setChangeMode}
      changeMode={changeMode}
      register={register}
      handleSubmit={handleSubmit(handleSubmitCallback)}
      success={success}
      serverMessage={serverMessage}
      referer={referer}
      disabled={disabled}
    />
  );
};

export default Profile;
