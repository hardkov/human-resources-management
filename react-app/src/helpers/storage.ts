import UserLoginData from '../types/UserLoginData';

const TOKEN_KEY = 'TOKEN';
const USER_DATA_KEY = 'USER_DATA';

const saveToken = (token: string): void => {
  localStorage.setItem(TOKEN_KEY, token);
};

const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY);
};

const getToken = (): string | null => {
  return localStorage.getItem(TOKEN_KEY);
};

const saveUserData = (userData: UserLoginData): void => {
  localStorage.setItem(USER_DATA_KEY, JSON.stringify(userData));
};

const removeUserData = () => {
  localStorage.removeItem(USER_DATA_KEY);
};

const getUserData = (): UserLoginData | null => {
  const userDataRaw = localStorage.getItem(USER_DATA_KEY);

  if (userDataRaw != null) return JSON.parse(userDataRaw);

  return null;
};

export { saveToken, removeToken, getToken, saveUserData, removeUserData, getUserData };
