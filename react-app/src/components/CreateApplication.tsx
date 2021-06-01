import React, { useState } from 'react';
import { makeStyles, Container, Radio, RadioGroup, FormControlLabel } from '@material-ui/core';
import { useForm } from 'react-hook-form';

import ApplicationType from '../types/ApplicationType';
import CreateApplicationForm from './CreateApplicationForm';
import { submitLeaveApplication } from '../services/applicationService';
import SubmitLeaveApplicationData from '../types/SubmitLeaveApplicationData';

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
  paid: boolean;
  destination: string;
  bonusAmount: number;
};

const CreateApplication: React.FC = () => {
  const classes = useStyles();
  const [applicationType, setApplicationType] = useState<ApplicationType>('LEAVE');
  const { register, handleSubmit, control, reset } = useForm<FormValues>();
  const [serverError, setServerError] = useState<string>('');

  const onChange = (event: React.ChangeEvent<any>) => {
    setServerError('');
    setApplicationType(event.target.value);
  };

  const onSubmit = async (data: any) => {
    if (applicationType === 'LEAVE') {
      const result = await submitLeaveApplication(data as SubmitLeaveApplicationData);

      if (result.success) {
        setServerError('');
        reset();
      } else if (result.errors) setServerError(result.errors[0]);
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
          serverError={serverError}
        />
      </div>
    </Container>
  );
};

export default CreateApplication;
