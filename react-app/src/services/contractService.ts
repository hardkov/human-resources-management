import axios, { AxiosResponse } from 'axios';
import ActionResult from '../types/ActionResult';
import { CONTRACT_ENDPOINT, USER_DATA_ENDPOINT } from './config';
import { getAuthHeaders } from '../helpers/auth';
import ContractData from '../types/ContractData';

const getAllContracts = async (): Promise<ActionResult<ContractData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(`${CONTRACT_ENDPOINT}`, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true, data: response.data, errors: [] };
    }

    return { success: false, errors: ['Contracts not found'] };
  } catch {
    return { success: false, errors: ['Contracts not found'] };
  }
};

const addContractToUser = async (contractData: ContractData, userId: string): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.post(
      `${CONTRACT_ENDPOINT}/${USER_DATA_ENDPOINT}/${userId}`,
      contractData,
      {
        headers: getAuthHeaders(),
      },
    );

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['Contract could not be created'] };
  } catch {
    return { success: false, errors: ['Contract could not be created'] };
  }
};

const deleteContract = async (id: string): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.delete(`${CONTRACT_ENDPOINT}/${id}`, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['Contract could not be deleted'] };
  } catch {
    return { success: false, errors: ['Contract could not be deleted'] };
  }
};

const updateContract = async (contractData: ContractData): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.put(CONTRACT_ENDPOINT, contractData, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['Contract could not be updated'] };
  } catch {
    return { success: false, errors: ['Contract could not be updated'] };
  }
};

export { getAllContracts, addContractToUser, updateContract, deleteContract };
