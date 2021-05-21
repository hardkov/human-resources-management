import React from 'react';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { UseFormRegister } from 'react-hook-form';

interface Props {
  register: UseFormRegister<any>;
  fieldName: string;
  textName: string;
  disabled: boolean;
}

const UserFormPasswordField: React.FC<Props> = ({ register, fieldName, textName, disabled }: Props) => {
  const { ref: formHookRef, ...formHookRest } = register(fieldName);

  return (
    <>
      <Grid item container alignItems="center" xs={3}>
        <Grid item>
          <Typography>{textName}</Typography>
        </Grid>
      </Grid>
      <Grid item xs={8}>
        <TextField {...formHookRest} inputRef={formHookRef} disabled={disabled} variant="outlined" type="password" fullWidth />
      </Grid>
    </>
  );
};

export default UserFormPasswordField;
