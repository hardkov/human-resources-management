import axios, { AxiosResponse } from 'axios';
import UserType from '../types/UserType';
import { LOGIN_ENDPOINT } from './config';
import { getUserData, removeToken, removeUserData, saveToken, saveUserData } from '../helpers/storage';
import UserLoginData from '../types/UserLoginData';
import ActionResult from '../types/ActionResult';

let currentUser: UserLoginData | null = getUserData();

const getUserType = (): UserType | undefined => {
  return currentUser?.authorities;
};

const getUserName = (): string | undefined => {
  return currentUser?.username;
};

const parseToken = (token: string): UserLoginData => {
  const userDataRaw: string = atob(token.split('.')[1]);
  const userDataObject = JSON.parse(userDataRaw);
  const { sub, username, authorities } = userDataObject;

  return { sub, username, userId: userDataObject.user_id, authorities };
};

const login = async (username: string, password: string): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.post(LOGIN_ENDPOINT, {
      username,
      password,
    });

    if (response.status === 200) {
      const token: string = response.headers.authorization;
      const userData: UserLoginData = parseToken(token);

      saveUserData(userData);
      saveToken(token);

      currentUser = userData;

      return { success: true, errors: [] };
    }

    return { success: false, errors: ['Login error'] };
  } catch {
    return { success: false, errors: ['Login error'] };
  }
};

const logout = () => {
  currentUser = null;
  removeUserData();
  removeToken();
};

const isLoggedIn = (): boolean => {
  return currentUser != null;
};

export { getUserType, getUserName, login, logout, isLoggedIn };
