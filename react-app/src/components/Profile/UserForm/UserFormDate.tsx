import React, { useEffect, useState } from 'react';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { UseFormSetValue } from 'react-hook-form';
import { MuiPickersUtilsProvider, KeyboardDatePicker } from '@material-ui/pickers';
// eslint-disable-next-line import/no-duplicates
import DateFnsAdapter from '@date-io/date-fns';
// eslint-disable-next-line import/no-duplicates
import DateFnsUtils from '@date-io/date-fns';
import { MaterialUiPickersDate } from '@material-ui/pickers/typings/date';

interface Props {
  setValue: UseFormSetValue<any>;
  fieldName: string;
  textName: string;
  disabled: boolean;
}

const UserFormDate: React.FC<Props> = ({ setValue, fieldName, textName, disabled }: Props) => {
  const [date, setDate] = useState<MaterialUiPickersDate>(null);

  useEffect(() => {
    const fnsAdapter = new DateFnsAdapter();
    if (date) {
      try {
        setValue(fieldName, fnsAdapter.format(date, 'yyyy-MM-dd'), { shouldValidate: true, shouldDirty: true });
      } catch {
        setValue(fieldName, null);
      }
    } else {
      setValue(fieldName, date, { shouldValidate: true, shouldDirty: true });
    }
  }, [date, setValue, fieldName]);

  return (
    <>
      <Grid item container alignItems="center" xs={3}>
        <Grid item>
          <Typography>{textName}</Typography>
        </Grid>
      </Grid>
      <Grid item xs={8}>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <KeyboardDatePicker
            maxDate={Date.now()}
            format="yyyy-MM-dd"
            value={date}
            onChange={setDate}
            disabled={disabled}
            inputVariant="outlined"
            fullWidth
          />
        </MuiPickersUtilsProvider>
      </Grid>
    </>
  );
};

export default UserFormDate;
