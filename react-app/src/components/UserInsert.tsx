import React, {useState} from 'react';
import { Redirect } from 'react-router-dom';

import ActionResult from '../types/ActionResult';
import {addUser} from "../services/userService";
import UserInsertForm from "./UserInsertForm";
import Header from "./Header";
import UserInsertionData from "../types/UserInsertionData";

const UserInsert = () => {
  const [redirect, setRedirect] = useState<boolean>(false);
  const [error, setErrors] = useState<string>('');


  const handleSubmitCallback = async (data: UserInsertionData) => {
    const result: ActionResult = await addUser(data);

    if (result.success) setRedirect(true);
    else if (result.errors) setErrors(result.errors[0]);
  };

  if (redirect) return <Redirect to="/home" />;
  return <>
    <Header />
    <UserInsertForm
      handleSubmitCallback={handleSubmitCallback}
      error={error} />;
    </>
};

export default UserInsert;
