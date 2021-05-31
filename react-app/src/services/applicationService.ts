import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import ActionResult from '../types/ActionResult';
import LeaveApplicationData from '../types/LeaveApplicationData';

import { LEAVE_APPLICATION_MANAGEMENT_ENDPOINT } from './config';

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

export { getLeaveApplications };
