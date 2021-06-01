import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import ActionResult from '../types/ActionResult';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';
import SubmitDelegationApplicationData from '../types/SubmitDelegationApplicationData';
import SubmitBonusApplicationData from '../types/SubmitBonusApplicationData';

import { LEAVE_APPLICATION_ENDPOINT, DELEGATION_APPLICATION_ENDPOINT, BONUS_APPLICATION_ENDPOINT } from './config';

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

export { submitLeaveApplication, submitDelegationApplication, submitBonusApplication };
