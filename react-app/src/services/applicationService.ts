import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import ActionResult from '../types/ActionResult';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';

import { LEAVE_APPLICATION_ENDPOINT } from './config';

const submitLeaveApplication = async (leaveApplicationData: SubmitLeaveApplicationData): Promise<ActionResult> => {
  try {
    const response: AxiosResponse = await axios.put(
      LEAVE_APPLICATION_ENDPOINT,
      { ...leaveApplicationData, status: 'PENDING' },
      {
        headers: getAuthHeaders(),
      },
    );

    if (response.status === 200) {
      return { success: true };
    }

    return { success: false, errors: ['Could not submit an application'] };
  } catch (error) {
    return { success: false, errors: ['Could not submit an application'] };
  }
};

export { submitLeaveApplication };
