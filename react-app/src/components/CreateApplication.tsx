import React, { useState } from 'react';
import { makeStyles, Container, Radio, RadioGroup, FormControlLabel } from '@material-ui/core';
import { useForm } from 'react-hook-form';

import ApplicationType from '../types/ApplicationType';
import CreateApplicationForm from './CreateApplicationForm';
import {
  submitBonusApplication,
  submitDelegationApplication,
  submitLeaveApplication,
} from '../services/applicationService';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';
import SubmitDelegationApplicationData from '../types/SubmitDelegationApplicationData';
import SubmitBonusApplicationData from '../types/SubmitBonusApplicationData';

const useStyles = makeStyles(() => ({
  paper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
}));

type FormValues = {
  place: string;
  content: string;
  startDate: string;
  endDate: string;
  startDatetime: string;
  endDatetime: string;
  paid: boolean;
  destination: string;
  money: number;
};

const CreateApplication: React.FC = () => {
  const classes = useStyles();
  const [applicationType, setApplicationType] = useState<ApplicationType>('LEAVE');
  const { register, handleSubmit, control, reset } = useForm<FormValues>();
  const [success, setSuccess] = useState<boolean>(false);
  const [serverMessage, setServerMessage] = useState<string>('');

  const onChange = (event: React.ChangeEvent<any>) => {
    setServerMessage('');
    setSuccess(false);
    setApplicationType(event.target.value);
  };

  const onSubmit = async (data: any) => {
    let result;

    if (applicationType === 'LEAVE') {
      result = await submitLeaveApplication(data as SubmitLeaveApplicationData);
    } else if (applicationType === 'DELEGATION') {
      result = await submitDelegationApplication({
        ...data,
        startDate: data.startDatetime,
        endDate: data.endDatetime,
        endDatetime: undefined,
        startDatetime: undefined,
      } as SubmitDelegationApplicationData);
    } else if (applicationType === 'BONUS') {
      result = await submitBonusApplication(data as SubmitBonusApplicationData);
    }

    if (result?.success) {
      setSuccess(true);
      setServerMessage('Success');
      reset();
    } else if (result?.errors) {
      setSuccess(false);
      setServerMessage(result.errors[0]);
    }
  };

  return (
    <Container maxWidth="sm">
      <div className={classes.paper}>
        <RadioGroup value={applicationType} onChange={onChange} row>
          <FormControlLabel value="LEAVE" control={<Radio />} label="Leave" />
          <FormControlLabel value="DELEGATION" control={<Radio />} label="Delegation" />
          <FormControlLabel value="BONUS" control={<Radio />} label="Bonus" />
        </RadioGroup>
        <CreateApplicationForm
          applicationType={applicationType}
          register={register}
          handleSubmit={handleSubmit(onSubmit)}
          control={control}
          success={success}
          serverMessage={serverMessage}
        />
      </div>
    </Container>
  );
};

export default CreateApplication;
