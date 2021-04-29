import React from "react";
import { Redirect } from "react-router-dom";

import LoginForm from "./LoginForm";
// import useForm from "../../hooks/useForm";
// import { loginUser } from "../../services/authService";

const Login = () => {
    // const [
    //     isLoading,
    //     errorMessage,
    //     redirect,
    //     handleCredentialsChange,
    //     handleSubmit,
    // ] = useForm(loginUser, { username: "", password: "" });
    //
    // if (redirect) return <Redirect to="/" />;

    return (
        <LoginForm
            // isLoading={isLoading}
            // errorMessage={errorMessage}
            // handleCredentialsChange={handleCredentialsChange}
            // handleSubmit={handleSubmit}
        />
    );
};

export default Login;