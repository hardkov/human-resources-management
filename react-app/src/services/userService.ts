import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import { USER_DATA_ENDPOINT } from './config';
import UserData from '../types/UserData';
import ActionResult from '../types/ActionResult';

const getUser = async (userId: string): Promise<ActionResult<UserData>> => {
  try {
    const response: AxiosResponse = await axios.get(`${USER_DATA_ENDPOINT}/${userId}`, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true, data: response.data, errors: [] };
    }

    return { success: false, errors: ['User not found'] };
  } catch {
    return { success: false, errors: ['User not found'] };
  }
};

const getAllUsers = async (): Promise<ActionResult<UserData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(USER_DATA_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true, data: response.data, errors: [] };
    }

    return { success: false, errors: ['User list fetch error'] };
  } catch {
    return { success: false, errors: ['User list fetch error'] };
  }
};

const addUser = async (userData: UserData): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.post(USER_DATA_ENDPOINT, userData, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['User could not be created'] };
  } catch {
    return { success: false, errors: ['User could not be created'] };
  }
};

const updateUser = async (userData: UserData): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.put(USER_DATA_ENDPOINT, userData, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['User could not be updated'] };
  } catch {
    return { success: false, errors: ['User could not be updated'] };
  }
};

export { getUser, getAllUsers, addUser, updateUser };
