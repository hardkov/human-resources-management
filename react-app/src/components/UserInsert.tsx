import React, {useCallback, useState} from 'react';
import { Redirect } from 'react-router-dom';

import ActionResult from '../types/ActionResult';
import UserData from "../types/UserData";
import {addUser} from "../services/userService";
import UserInsertForm from "./UserInsertForm";

const UserInsert = () => {
  const [details, setDetails] = useState<UserData>({
    id: null,
    position: "",
    personalData:{
      id: null,
      firstname: "",
      lastname: "",
      email: "",
      phoneNumber: "",
      address: "",
      birthdate: "",
      thumbnail: null
  }});
  const [redirect, setRedirect] = useState<boolean>(false);
  const [error, setErrors] = useState<string>('');

  const handleUserChange = (event: React.FormEvent) => {
    const target = event.target as HTMLInputElement;
    setDetails({ ...details, [target.name]: target.value });
    console.log(details)
  };

  const handlePersonalDataChange = (event: React.FormEvent) => {
    const target = event.target as HTMLInputElement;
    setDetails({ ...details, personalData: {...details.personalData, [target.name]: target.value} });
    console.log(details)
  };

  const handleBirthdateChange = useCallback((date: Date) => {
    setDetails(d => {
      return {...d, personalData: {
        ...d.personalData, birthdate:
              `${date.getFullYear()}-${`0${date.getMonth()+1}`.slice(-2)}-${`0${date.getDate()}`.slice(-2)}`}}
    });
  }, []);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const result: ActionResult = await addUser(details);

    if (result.success) setRedirect(true);
    else if (result.errors) setErrors(result.errors[0]);
  };

  if (redirect) return <Redirect to="/home" />;
  return <UserInsertForm
      handleSubmit={handleSubmit}
      handleUserChange={handleUserChange}
      handlePersonalDataChange={handlePersonalDataChange}
      handleBirthdateChange={handleBirthdateChange}
      error={error} />;
};

export default UserInsert;
