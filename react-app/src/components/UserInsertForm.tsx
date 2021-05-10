import 'date-fns';
import React, {useCallback, useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {FormControl} from "@material-ui/core";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import InputLabel from "@material-ui/core/InputLabel";

import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker
} from '@material-ui/pickers';
import UserInsertionData from "../types/UserInsertionData";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.primary.main
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    input: {
        width: '100%'
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    progress: {
        color: theme.palette.primary.dark,
    },
}));

interface Props {
    handleSubmitCallback: (data: UserInsertionData) => void;
    error: string;
}

const UserInsertForm = ({ handleSubmitCallback, error }: Props) => {
    const classes = useStyles();

    const [selectedDate, setSelectedDate] = React.useState(new Date(2021, 0, 1))
    const [details, setDetails] = useState<UserInsertionData>({
        position: "",
        username: "",
        role: "EMPLOYEE",
        password: "",
        personalData:{
            id: 0,
            firstname: "",
            lastname: "",
            email: "",
            phoneNumber: "",
            address: "",
            birthdate: "",
            thumbnail: null
        }});

    const handleDateSelectionChange = (date: Date | null) => {
        if(date){
            setSelectedDate(date)
        }
    }

    const handleUserChange = (event: React.FormEvent) => {
        const target = event.target as HTMLInputElement;
        setDetails({ ...details, [target.name]: target.value });
        console.log(details)
    };

    const handleUserChangeInput = (event: React.ChangeEvent<{ value: unknown }>) => {
        const target = event.target as HTMLInputElement;
        setDetails({ ...details, [target.name]: target.value });
        console.log(details)
    };

    const handlePersonalDataChange = (event: React.FormEvent) => {
        const target = event.target as HTMLInputElement;
        setDetails({ ...details, personalData: {...details.personalData, [target.name]: target.value} });
        console.log(details)
    };

    const handleBirthdateChange = useCallback((date: Date) => {
        setDetails(d => {
            return {...d, personalData: {
                    ...d.personalData, birthdate:
                        `${date.getFullYear()}-${`0${date.getMonth()+1}`.slice(-2)}-${`0${date.getDate()}`.slice(-2)}`}}
        });
    }, []);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault()
        handleSubmitCallback(details)
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
                        <Grid item xs={6}>
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
                        <Grid item xs={6}>
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
                                    fullWidth
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
                            <FormControl required variant="outlined" className={classes.input}>
                                <InputLabel id="roleLabel">Role in company</InputLabel>
                                <Select
                                    id="role"
                                    name="role"
                                    labelId="roleLabel"
                                    label="Role in company"
                                    defaultValue="EMPLOYEE"
                                    onChange={handleUserChangeInput}
                                >
                                    <MenuItem value="EMPLOYEE">Employee</MenuItem>
                                    <MenuItem value="SUPERVISOR">Supervisor</MenuItem>
                                </Select>
                            </FormControl>
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
                        <Grid item xs={6}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="username"
                                label="Username"
                                id="username"
                                onChange={handleUserChange}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="password"
                                type="password"
                                label="Password"
                                id="password"
                                onChange={handleUserChange}
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
