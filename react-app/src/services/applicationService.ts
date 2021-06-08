import axios, { AxiosResponse } from 'axios';
import { getAuthHeaders } from '../helpers/auth';
import ActionResult from '../types/ActionResult';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';
import SubmitDelegationApplicationData from '../types/SubmitDelegationApplicationData';
import SubmitBonusApplicationData from '../types/SubmitBonusApplicationData';
import LeaveApplicationData from '../types/LeaveApplicationData';
import BonusApplicationData from '../types/BonusApplicationData';
import DelegationApplicationData from '../types/DelegationApplicationData';

import {
  LEAVE_APPLICATION_ENDPOINT,
  DELEGATION_APPLICATION_ENDPOINT,
  BONUS_APPLICATION_ENDPOINT,
  LEAVE_APPLICATION_MANAGEMENT_ENDPOINT,
  BONUS_APPLICATION_MANAGEMENT_ENDPOINT,
  DELEGATION_APPLICATION_MANAGEMENT_ENDPOINT,
  DOWNLOAD_LEAVE_APPLICATION_ENDPOINT,
  DOWNLOAD_BONUS_APPLICATION_ENDPOINT,
  DOWNLOAD_DELEGATION_APPLICATION_ENDPOINT,
} from './config';
import ApplicationStatus from '../types/ApplicationStatus';
import ApplicationType from '../types/ApplicationType';

const getLeaveApplications = async (): Promise<ActionResult<LeaveApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(LEAVE_APPLICATION_MANAGEMENT_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not fetch applications'] };
  }
};

const getBonusApplications = async (): Promise<ActionResult<BonusApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(BONUS_APPLICATION_MANAGEMENT_ENDPOINT, {
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

const getDelegationApplications = async (): Promise<ActionResult<DelegationApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(DELEGATION_APPLICATION_MANAGEMENT_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not fetch applications'] };
  }
};

const getMyLeaveApplications = async (): Promise<ActionResult<LeaveApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(LEAVE_APPLICATION_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not fetch applications'] };
  }
};

const getMyBonusApplications = async (): Promise<ActionResult<BonusApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(BONUS_APPLICATION_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not fetch applications'] };
  }
};

const getMyDelegationApplications = async (): Promise<ActionResult<DelegationApplicationData[]>> => {
  try {
    const response: AxiosResponse = await axios.get(DELEGATION_APPLICATION_ENDPOINT, {
      headers: getAuthHeaders(),
    });

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not fetch applications'] };
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

const updateApplication = async (
  id: number,
  status: ApplicationStatus,
  type: ApplicationType,
): Promise<ActionResult> => {
  let endpoint;

  switch (type) {
    case 'LEAVE':
      endpoint = LEAVE_APPLICATION_MANAGEMENT_ENDPOINT;
      break;
    case 'BONUS':
      endpoint = BONUS_APPLICATION_MANAGEMENT_ENDPOINT;
      break;
    case 'DELEGATION':
      endpoint = DELEGATION_APPLICATION_MANAGEMENT_ENDPOINT;
      break;
    default:
      endpoint = '';
  }

  try {
    await axios.post(
      endpoint,
      { applicationId: id, status },
      {
        headers: getAuthHeaders(),
      },
    );

    return { success: true };
  } catch (error) {
    return { success: false, errors: error.response.data.errors || ['Could not update application'] };
  }
};

const downloadApplication = (id: number, type: ApplicationType) => {
  let endpoint;

  switch (type) {
    case 'LEAVE':
      endpoint = DOWNLOAD_LEAVE_APPLICATION_ENDPOINT;
      break;
    case 'BONUS':
      endpoint = DOWNLOAD_BONUS_APPLICATION_ENDPOINT;
      break;
    case 'DELEGATION':
      endpoint = DOWNLOAD_DELEGATION_APPLICATION_ENDPOINT;
      break;
    default:
      endpoint = '';
  }

  axios
    .get(`${endpoint}/${id}`, {
      headers: getAuthHeaders(),
      responseType: 'blob',
    })
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'application.pdf');
      document.body.appendChild(link);
      link.click();
    });
};

export {
  submitLeaveApplication,
  submitDelegationApplication,
  submitBonusApplication,
  getLeaveApplications,
  getBonusApplications,
  getDelegationApplications,
  getMyLeaveApplications,
  getMyBonusApplications,
  getMyDelegationApplications,
  updateApplication,
  downloadApplication,
};
