import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { setData } from "../redux/loginResponseReducer";
import { useNavigate } from "react-router-dom";

const ClientType = {
  EMPTY: "",
  ADMIN: "ADMIN",
  COMPANY: "COMPANY",
  CUSTOMER: "CUSTOMER",
};

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [clientType, setClientType] = useState(ClientType.EMPTY);

  
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleClientTypeChange = (e) => {
    const value = e.target.value;
    if (Object.values(ClientType).includes(value)) {
      setClientType(value);
    } else {
      console.error("Invalid client type selected");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (clientType === ClientType.EMPTY) {
      alert("Please select a valid client type.");
      return;
    }

    const loginRequest = {
      email: email,
      password: password,
      clientType: clientType,
    };

    axios
      .post("http://localhost:8080/login", loginRequest)
      .then((response) => {
        localStorage.setItem("token", response.data.token);
        dispatch(setData(response.data));
        console.log("Login successful: ", response.data);

        if (response.data.clientType === "ADMIN") {
          navigate("/admin");
        }

        if (response.data.clientType === "COMPANY") {
          navigate("/company");
        }

        if (response.data.clientType === "CUSTOMER") {
          navigate("/customer");
        }
      })
      .catch((error) => {
        console.error("Login failed: ", error);
        alert("Login failed: " + error);
      });
  };

  return (
    <div className="text-center">
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Email: </label>
          <input
            className="form-control"
            type="text"
            name="email"
            placeholder="Email"
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Password: </label>
          <input
            className="form-control"
            type="password"
            name="password"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="mb-3">
        <label className="form-label">Client Type: </label>
          <select
            className="form-control"
            name="clientType"
            value={clientType}
            onChange={handleClientTypeChange}
          >
            <option value={ClientType.EMPTY}>Select Client Type: </option>
            <option value={ClientType.ADMIN}>Admin</option>
            <option value={ClientType.COMPANY}>Company</option>
            <option value={ClientType.CUSTOMER}>Customer</option>
          </select>
        </div>
        <div>
          <button type="submit" className="btn btn-primary btn-lg">
            Login
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
