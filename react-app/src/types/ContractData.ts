import UserData from './UserData';
import ContractType from './ContractType';

interface ContractData {
  id: number;
  user: UserData;
  startDate: string;
  endDate: string;
  contractType: ContractType;
  baseSalary: number;
}

export default ContractData;
