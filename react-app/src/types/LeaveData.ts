import UserData from './UserData';

interface LeaveData {
    id: number;
    userData: UserData;
    position: string;
    startDate: string;
    endDate: string;
    paid: boolean;
    thumbnail: string[] | null;
}

export default LeaveData;
