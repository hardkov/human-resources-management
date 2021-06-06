import ApplicationStatus from './ApplicationStatus';

interface SubmitDelegationApplicationData {
  content: string;
  place: string;
  status: ApplicationStatus;
  startDate: string;
  endDate: string;
  destination: string;
}

export default SubmitDelegationApplicationData;
