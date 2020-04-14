import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import CircularProgress from "@material-ui/core/CircularProgress";
import { Link } from "react-router-dom";

const useStyles = makeStyles(theme => ({
  table: {
    minWidth: 600
  },
  paper: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    margin: `10px`,
    height: "100%",
    width: "99%",
    marginTop: theme.spacing(7)
  }
}));

export default function SimpleTable() {
  const classes = useStyles();

  const [data, updateData] = React.useState([]);
  const [firstLoad, setLoad] = React.useState(true);
  let isLoading = true;

  async function listVisitors() {
    let response = await fetch("/api/v1/visitor");
    let body = await response.json();
    updateData(body);
  }

  if (firstLoad) {
    listVisitors();
    setLoad(false);
  }

  if (data.length > 0) isLoading = false;

  return (
    <div className={classes.paper}>
      <Typography component="h1" variant="h5">
        Visitor List
      </Typography>

      {isLoading ? (
        <CircularProgress />
      ) : (
        <TableContainer
          style={{ width: "80%", margin: "0 10px" }}
          component={Paper}
        >
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="center">ID</TableCell>
                <TableCell align="center">IP Address</TableCell>
                <TableCell align="center">Country Code</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data?.map(row => (
                <TableRow key={row.id}>
                  <TableCell align="center">{row.id}</TableCell>
                  <TableCell align="center">{row.ip}</TableCell>
                  <TableCell align="center">{row.countryCode}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
              <Link to="/">

        <Typography align="left">
          &#x2190; Add another visitor
        </Typography>{" "}
        </Link>
    </div>
  );
}