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

const getUserId = (): number | undefined => {
  return currentUser?.userId;
};

const parseToken = (token: string): UserLoginData => {
  const userDataRaw: string = atob(token.split('.')[1]);
  const userDataObject = JSON.parse(userDataRaw);
  const { sub, username, authorities } = userDataObject;

  return { sub, username, userId: userDataObject.user_id, authorities };
};

const login = async (username: string, password: string): Promise<ActionResult> => {
  return axios
    .post(LOGIN_ENDPOINT, {
      username,
      password,
    })
    .then((response) => {
      const token: string = response.headers.authorization;
      const userData: UserLoginData = parseToken(token);

      saveUserData(userData);
      saveToken(token);

      currentUser = userData;

      return { success: true, errors: [] };
    })
    .catch((error) => {
      return { success: false, errors: error.response.data.errors || ['Login error'] };
    });
};

const logout = () => {
  currentUser = null;
  removeUserData();
  removeToken();
};

const isLoggedIn = (): boolean => {
  return currentUser != null;
};

export { getUserType, getUserName, login, logout, isLoggedIn, getUserId };
