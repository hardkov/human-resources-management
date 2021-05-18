import React from 'react';
import { TextField, Button, makeStyles, Checkbox, Grid, FormControlLabel } from '@material-ui/core';
import { Controller, UseFormRegister, Control } from 'react-hook-form';

import ApplicationType from '../types/ApplicationType';

const useStyles = makeStyles((theme) => ({
  form: {
    margin: theme.spacing(5),
    width: '100%',
  },

  submit: {
    marginTop: theme.spacing(5),
  },
}));

interface Props {
  applicationType: ApplicationType;
  register: UseFormRegister<any>;
  handleSubmit: () => void;
  control: Control<any>;
}

const CreateApplicationForm: React.FC<Props> = ({ applicationType, register, handleSubmit, control }: Props) => {
  const classes = useStyles();

  return (
    <form className={classes.form} noValidate onSubmit={handleSubmit}>
      <Grid container spacing={3}>
        <Grid item xs={6}>
          <TextField {...register('place')} label="Place" variant="outlined" fullWidth />
        </Grid>
        <Grid item xs={6}>
          <TextField
            {...register('date')}
            type="date"
            label="Date"
            variant="outlined"
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField {...register('content')} label="Content" variant="outlined" multiline rows={5} fullWidth />
        </Grid>
        {(applicationType === 'LEAVE' || applicationType === 'DELEGATION') && (
          <>
            <Grid item xs={6}>
              <TextField
                {...register('startDate', { shouldUnregister: true })}
                type="date"
                label="Start date"
                variant="outlined"
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                {...register('endDate', { shouldUnregister: true })}
                type="date"
                label="End date"
                variant="outlined"
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
          </>
        )}
        {applicationType === 'LEAVE' && (
          <Grid item xs={12}>
            <Controller
              name="paid"
              control={control}
              defaultValue={false}
              shouldUnregister
              render={({ field }) => <FormControlLabel label="Paid leave" control={<Checkbox {...field} />} />}
            />
          </Grid>
        )}
        {applicationType === 'DELEGATION' && (
          <Grid item xs={6}>
            <TextField
              {...register('destination', { shouldUnregister: true })}
              label="Destination"
              variant="outlined"
              fullWidth
            />
          </Grid>
        )}
        {applicationType === 'BONUS' && (
          <Grid item xs={6}>
            <TextField
              {...register('bonusAmount', { shouldUnregister: true })}
              type="number"
              label="Bonus amount"
              variant="outlined"
              fullWidth
            />
          </Grid>
        )}
      </Grid>
      <Button className={classes.submit} type="submit" fullWidth variant="contained" color="primary">
        Submit
      </Button>
    </form>
  );
};

export default CreateApplicationForm;
