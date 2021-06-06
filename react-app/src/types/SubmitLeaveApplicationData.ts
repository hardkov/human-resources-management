import ApplicationStatus from './ApplicationStatus';

interface SubmitLeaveApplicationData {
  content: string;
  place: string;
  status: ApplicationStatus;
  startDate: string;
  endDate: string;
  paid: boolean;
}

export default SubmitLeaveApplicationData;
