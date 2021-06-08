const API_ENDPOINT = '/api';

const EMPLOYEE_APPLICATION_ENDPOINT = `${API_ENDPOINT}/employee/application`;
const SUPERVISOR_APPLICATION_ENDPOINT = `${API_ENDPOINT}/supervisor/application-management`;
const LOGIN_ENDPOINT = `${API_ENDPOINT}/public/login`;
const USER_DATA_ENDPOINT = `${API_ENDPOINT}/user`;
const LEAVE_ENDPOINT = `${API_ENDPOINT}/leave`;
const PERMISSION_ENDPOINT = `${API_ENDPOINT}/permissions`;
const UPDATE_PERMISSION_ENDPOINT = `${API_ENDPOINT}/permission`;
const CONTRACT_ENDPOINT = `${API_ENDPOINT}/contract`;
const LEAVE_APPLICATION_ENDPOINT = `${EMPLOYEE_APPLICATION_ENDPOINT}/leave-application`;
const DELEGATION_APPLICATION_ENDPOINT = `${EMPLOYEE_APPLICATION_ENDPOINT}/delegation-application`;
const BONUS_APPLICATION_ENDPOINT = `${EMPLOYEE_APPLICATION_ENDPOINT}/bonus-application`;
const LEAVE_APPLICATION_MANAGEMENT_ENDPOINT = `${SUPERVISOR_APPLICATION_ENDPOINT}/leave-application`;
const DELEGATION_APPLICATION_MANAGEMENT_ENDPOINT = `${SUPERVISOR_APPLICATION_ENDPOINT}/delegation-application`;
const BONUS_APPLICATION_MANAGEMENT_ENDPOINT = `${SUPERVISOR_APPLICATION_ENDPOINT}/bonus-application`;
const DONWLOAD_APPLICATION_ENDPOINT = `${API_ENDPOINT}/pdf`;
const DOWNLOAD_LEAVE_APPLICATION_ENDPOINT = `${DONWLOAD_APPLICATION_ENDPOINT}/leave-application`;
const DOWNLOAD_BONUS_APPLICATION_ENDPOINT = `${DONWLOAD_APPLICATION_ENDPOINT}/bonus-application`;
const DOWNLOAD_DELEGATION_APPLICATION_ENDPOINT = `${DONWLOAD_APPLICATION_ENDPOINT}/delegation-application`;

export {
  LOGIN_ENDPOINT,
  USER_DATA_ENDPOINT,
  LEAVE_ENDPOINT,
  CONTRACT_ENDPOINT,
  PERMISSION_ENDPOINT,
  UPDATE_PERMISSION_ENDPOINT,
  LEAVE_APPLICATION_ENDPOINT,
  BONUS_APPLICATION_ENDPOINT,
  DELEGATION_APPLICATION_ENDPOINT,
  LEAVE_APPLICATION_MANAGEMENT_ENDPOINT,
  BONUS_APPLICATION_MANAGEMENT_ENDPOINT,
  DELEGATION_APPLICATION_MANAGEMENT_ENDPOINT,
  DOWNLOAD_LEAVE_APPLICATION_ENDPOINT,
  DOWNLOAD_BONUS_APPLICATION_ENDPOINT,
  DOWNLOAD_DELEGATION_APPLICATION_ENDPOINT,
};
