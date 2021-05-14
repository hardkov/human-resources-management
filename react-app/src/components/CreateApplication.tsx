import React, { useState } from 'react';
import { makeStyles, Container, Radio, RadioGroup, FormControlLabel } from '@material-ui/core';
import { useForm } from 'react-hook-form';

import ApplicationType from '../types/ApplicationType';
import CreateApplicationForm from './CreateApplicationForm';

const useStyles = makeStyles(() => ({
  paper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
}));

type FormValues = {
  place: string;
  date: string;
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
  const { register, handleSubmit, control } = useForm<FormValues>();

  const onChange = (event: React.ChangeEvent<any>) => {
    setApplicationType(event.target.value);
  };

  const onSubmit = (data: any) => {
    // api call here
    console.log(data);
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
        />
      </div>
    </Container>
  );
};

export default CreateApplication;
