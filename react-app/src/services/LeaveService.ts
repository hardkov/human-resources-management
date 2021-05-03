import axios, {AxiosResponse} from "axios";
import ActionResult from "../types/ActionResult";
import LeaveData from "../types/LeaveData";
import {LEAVE_ENDPOINT, USER_DATA_ENDPOINT} from "./config";
import {getAuthHeaders} from "../helpers/auth";

const getAllLeaves = async (): Promise<ActionResult<LeaveData[]>> => {
    try {
        const response: AxiosResponse = await axios.get(`${LEAVE_ENDPOINT}`, {
            headers: getAuthHeaders(),
        });

        if (response.status === 200) {
            return { success: true, data: response.data, errors: [] };
        }

        return { success: false, errors: ['Leaves not found'] };
    } catch {
        return { success: false, errors: ['Leaves not found'] };
    }
};

const getLeaveByUser = async (userId: string): Promise<ActionResult<LeaveData>> => {
    try {
        const response: AxiosResponse = await axios.get(`${LEAVE_ENDPOINT}/${userId}`, {
            headers: getAuthHeaders(),
        });

        if (response.status === 200) {
            return { success: true, data: response.data, errors: [] };
        }

        return { success: false, errors: ['Leaves not found'] };
    } catch {
        return { success: false, errors: ['Leaves not found'] };
    }
};



const addLeaveToUser = async (leaveData: LeaveData, userId: string): Promise<ActionResult> => {
    try {
        const response: AxiosResponse = await axios.post(`${LEAVE_ENDPOINT}/${USER_DATA_ENDPOINT}/${userId}`, leaveData, {
            headers: getAuthHeaders(),
        });

        if (response.status === 200) {
            return { success: true };
        }

        return { success: false, errors: ['Leave could not be created'] };
    } catch {
        return { success: false, errors: ['Leave could not be created'] };
    }
};

const deleteLeave = async (id: string): Promise<ActionResult> => {
    try {
        const response: AxiosResponse = await axios.delete(`${LEAVE_ENDPOINT}/${id}`, {
            headers: getAuthHeaders(),
        });

        if (response.status === 200) {
            return { success: true };
        }

        return { success: false, errors: ['Leave could not be deleted'] };
    } catch {
        return { success: false, errors: ['Leave could not be deleted'] };
    }
};

const updateLeave = async (leaveData: LeaveData): Promise<ActionResult> => {
    try {
        const response: AxiosResponse = await axios.put(LEAVE_ENDPOINT, leaveData, {
            headers: getAuthHeaders(),
        });

        if (response.status === 200) {
            return { success: true };
        }

        return { success: false, errors: ['Leave could not be updated'] };
    } catch {
        return { success: false, errors: ['Leave could not be updated'] };
    }
};
export {getAllLeaves, getLeaveByUser, addLeaveToUser, deleteLeave, updateLeave}