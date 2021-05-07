import 'date-fns';
import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker
} from '@material-ui/pickers';

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(16),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.primary.main,
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    progress: {
        color: theme.palette.primary.dark,
    },
}));

interface Props {
    handleSubmit: (event: React.FormEvent) => void;
    handleUserChange: (event: React.FormEvent) => void;
    handlePersonalDataChange: (event: React.FormEvent) => void;
    handleBirthdateChange: (date: Date) => void;
    error: string;
}

const UserInsertForm = ({ handleSubmit, handleUserChange, handlePersonalDataChange, handleBirthdateChange, error }: Props) => {
    const classes = useStyles();

    const [selectedDate, setSelectedDate] = React.useState(new Date(2021, 0, 1))

    const handleDateSelectionChange = (date: Date | null) => {
        if(date){
            setSelectedDate(date)
        }
    }

    React.useEffect(() => {
        handleBirthdateChange(selectedDate)
        console.log("halo")
    }, [selectedDate, handleBirthdateChange])

    return (
        <Container component="main" maxWidth="xs">
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Create new user
                </Typography>
                <form className={classes.form} noValidate onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                name="firstname"
                                variant="outlined"
                                required
                                fullWidth
                                id="firstname"
                                label="First name"
                                onChange={handlePersonalDataChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="lastname"
                                label="Last name"
                                id="lastname"
                                onChange={handlePersonalDataChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="dd/MM/yyyy"
                                    margin="normal"
                                    id="birthdate"
                                    label="Birthdate"
                                    value={selectedDate}
                                    onChange={handleDateSelectionChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="position"
                                label="Position"
                                id="position"
                                onChange={handleUserChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="email"
                                label="E-mail"
                                id="email"
                                onChange={handlePersonalDataChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="phoneNumber"
                                label="Phone Number"
                                id="phoneNumber"
                                onChange={handlePersonalDataChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="address"
                                label="Address"
                                id="address"
                                onChange={handlePersonalDataChange}
                            />
                        </Grid>
                    </Grid>
                    <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
                        Add User
                    </Button>
                </form>
                <Typography color="error">{error}</Typography>
            </div>
        </Container>
    );
};

export default UserInsertForm;
