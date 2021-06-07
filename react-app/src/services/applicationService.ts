import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import ActionResult from '../types/ActionResult';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';
import SubmitDelegationApplicationData from '../types/SubmitDelegationApplicationData';
import SubmitBonusApplicationData from '../types/SubmitBonusApplicationData';
import LeaveApplicationData from '../types/LeaveApplicationData';
import {
  LEAVE_APPLICATION_ENDPOINT,
  DELEGATION_APPLICATION_ENDPOINT,
  BONUS_APPLICATION_ENDPOINT,
  LEAVE_APPLICATION_MANAGEMENT_ENDPOINT,
} from './config';

const getLeaveApplications = async (): Promise<ActionResult<LeaveApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(LEAVE_APPLICATION_MANAGEMENT_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    if (response.status === 200) {
      return { success: true, data: response.data };
    }

    return { success: false, errors: ['Could not fetch applications'] };
  } catch {
    return { success: false, errors: ['Could not fetch applications'] };
  }
};

const submitLeaveApplication = async (leaveApplicationData: SubmitLeaveApplicationData): Promise<ActionResult> => {
  try {
    await axios.put(
      LEAVE_APPLICATION_ENDPOINT,
      { ...leaveApplicationData, status: 'PENDING' },
      {
        headers: getAuthHeaders(),
      },
    );

    return { success: true };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not submit an application'] };
  }
};

const submitDelegationApplication = async (
  delegationApplicationData: SubmitDelegationApplicationData,
): Promise<ActionResult> => {
  try {
    await axios.put(
      DELEGATION_APPLICATION_ENDPOINT,
      { ...delegationApplicationData, status: 'PENDING' },
      {
        headers: getAuthHeaders(),
      },
    );

    return { success: true };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not submit an application'] };
  }
};

const submitBonusApplication = async (bonusApplicationData: SubmitBonusApplicationData): Promise<ActionResult> => {
  try {
    await axios.put(
      BONUS_APPLICATION_ENDPOINT,
      { ...bonusApplicationData, status: 'PENDING' },
      {
        headers: getAuthHeaders(),
      },
    );

    return { success: true };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not submit an application'] };
  }
};

export { submitLeaveApplication, submitDelegationApplication, submitBonusApplication, getLeaveApplications };
