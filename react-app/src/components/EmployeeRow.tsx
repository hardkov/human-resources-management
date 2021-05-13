import React from 'react';
import { Link } from 'react-router-dom';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import IconButton from '@material-ui/core/IconButton';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import Collapse from '@material-ui/core/Collapse';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import { makeStyles } from '@material-ui/core';
import EditIcon from '@material-ui/icons/Edit';

const useStyles = makeStyles((theme) => ({
  buttonContainer: {
    display: 'flex',
    flex: 1,
  },

  button: {
    margin: theme.spacing(2),
  },
}));

const EmployeeRow = (props: any): JSX.Element => {
  const styles = useStyles();
  const { row } = props;
  const [open, setOpen] = React.useState(false);
  return (
    <>
      <TableRow>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.personalData.firstname}
        </TableCell>
        <TableCell component="th" scope="row">
          {row.personalData.lastname}
        </TableCell>
        <TableCell component="th" scope="row">
          {row.position}
        </TableCell>
        <TableCell>
          <div className={styles.buttonContainer}>
            <IconButton
              className={styles.button}
              color="secondary"
              component={Link}
              to={{ pathname: '/employee-profile', state: { userData: row, referer: '/employees' } }}
            >
              <EditIcon />
            </IconButton>
          </div>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box margin={1}>
              <Typography variant="h6" gutterBottom component="div">
                Details
              </Typography>
              <Table size="small">
                <TableHead>
                  <TableRow>
                    <TableCell>Email</TableCell>
                    <TableCell>Phone</TableCell>
                    <TableCell>Address</TableCell>
                    <TableCell>Birthdate</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  <TableRow key={row.id}>
                    <TableCell component="th" scope="row">
                      {row.personalData.email}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {row.personalData.phoneNumber}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {row.personalData.address}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {row.personalData.birthdate}
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </>
  );
};

export default EmployeeRow;
