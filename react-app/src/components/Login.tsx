import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';

import LoginForm from './LoginForm';
import { login } from '../services/authService';
import ActionResult from '../types/ActionResult';

interface Credentials {
  username: string;
  password: string;
}

const Login = () => {
  const [credentials, setCredentials] = useState<Credentials>({ username: '', password: '' });
  const [redirect, setRedirect] = useState<boolean>(false);
  const [error, setErrors] = useState<string>('');

  const handleCredentialsChange = (event: React.FormEvent) => {
    const target = event.target as HTMLInputElement;
    setCredentials({ ...credentials, [target.name]: target.value });
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const result: ActionResult = await login(credentials.username, credentials.password);

    if (result.success) setRedirect(true);
    else if (result.errors) setErrors(result.errors[0]);
  };

  if (redirect) return <Redirect to="/" />;
  return <LoginForm handleSubmit={handleSubmit} handleCredentialsChange={handleCredentialsChange} error={error} />;
};

export default Login;
