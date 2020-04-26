import React from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import Typography from "@material-ui/core/Typography";
import Cookies from 'js-cookie';

export default function AddVisitor() {
    const [ip, setIp] = React.useState("");
    const handleIpChange = event => setIp(event.target.value);

    async function sendRequest() {
        const response = await fetch(
            `/api/v1/visitor/${ip}`, 
            {
                method: "POST", 
                headers: {
                    'X-XSRF-TOKEN':  Cookies.get('XSRF-TOKEN'),
                },
                credentials: "include",
            }
        );
        let status = await response.ok;
        console.log(status);
    }

    const handleSubmit = ip => {
        sendRequest(ip);
        setIp("");
    }
    return (
        <>
        <TextField
                id="ip"
                label="ip"
                type="string"
                onChange={handleIpChange}
              />
        <Button preventDefault onClick={handleSubmit}>
            Save
        </Button>
        <Link to="/visitors">
            <Typography align="left">
            List visitors
            </Typography>{" "}
        </Link>
        </>
    )
}