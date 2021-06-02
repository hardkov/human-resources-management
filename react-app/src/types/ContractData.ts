import UserData from './UserData';

declare const enum ContractType {
    UoP,
    B2B,
    mandateContract
}

interface ContractData {
    id: number;
    user: UserData;
    startDate: string;
    endDate: string;
    contract: ContractType;
    baseSalary: number;

}

export default ContractData;