import React, {useEffect, useState} from 'react';
import { DataGrid } from '@material-ui/data-grid';
import {makeStyles} from "@material-ui/core";
import {getAllLeaves} from "../services/LeaveService";
import LeaveData from "../types/LeaveData";
import ActionResult from "../types/ActionResult";
import {leaves} from "./Mock/Leaves";

const useStyles = makeStyles(() => ({
    card: {
        position: 'relative',
        height: '300px',
        width: '100%',
    },
    cardActions: {
        position: 'absolute',
        right: 0,
        bottom: 0,
    },
}));

const LeavesView: React.FC = () => {
    // const classes = useStyles();
    // const [leaves, setLeaves] = useState<LeaveData[]>([]);
    // const data = getAllLeaves();
    //
    // useEffect(() => {
    //     const apiCall = async () => {
    //         const fetchedLeaves: ActionResult<LeaveData[]> = await getAllLeaves();
    //         if(fetchedLeaves.success)
    //             // setLeaves(fetchedLeaves.data)
    //            console.log(fetchedLeaves.data)
    //     };
    //     apiCall()
    // }, [])


    const columns = [
        { field: 'firstName', headerName: 'First name', width: 300 },
        { field: 'lastName', headerName: 'Last name', width: 300 },
        {field: 'startDate', headerName: 'Start', width: 300,},
        {field: 'endDate', headerName: 'End', width: 300},
        {field: 'paid', headerName: 'Paid', width: 300},
    ];

    const rows: any = []

        leaves.forEach(a => rows.push({"id": a.id, "firstName": a.user.personalData.firstname, "lastName": a.user.personalData.lastname,
            "startDate": a.startDate, "endDate": a.endDate,"paid": a.paid ? "yes" : "no"}));

    console.log(rows)


        return (
            <div style={{ height: 1000, width: '100%' }}>
                <DataGrid rows={rows} columns={columns} pageSize={20}  />
            </div>

        );


}

export default LeavesView;