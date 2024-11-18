import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";

const UpdateCustomerForm = () => {
  const [customerFirstName, setCustomerFirstName] = useState("");
  const [customerLastName, setCustomerLastName] = useState("");
  const [customerEmail, setCustomerEmail] = useState("");
  const [customerPassword, setCustomerPassword] = useState("");

  const token = localStorage.getItem("token");
  const body = useSelector((state) => state.modal.customerBody);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedCustomer = {
      customerId: body.customerId,
      firstName: customerFirstName,
      lastName: customerLastName,
      email: customerEmail,
      password: customerPassword,
    };

    axios
      .put("http://localhost:8080/admin/customer", updatedCustomer, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        console.log("Customer updated successfuly: ", response.data);
        alert("Customer updated successfully");
        setCustomerFirstName("");
        setCustomerLastName("");
        setCustomerEmail("");
        setCustomerPassword("");
      })
      .catch((error) => {
        console.error("Failed to update customer: ", error);
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">First Name: </label>
          <input
            className="form-control"
            type="text"
            name="firstName"
            value={customerFirstName}
            placeholder="Enter First Name"
            onChange={(e) => setCustomerFirstName(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Last Name: </label>
          <input
            className="form-control"
            type="text"
            name="lastName"
            value={customerLastName}
            placeholder="Enter Last Name"
            onChange={(e) => setCustomerLastName(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Email: </label>
          <input
            className="form-control"
            type="email"
            name="email"
            value={customerEmail}
            placeholder="Enter Email"
            onChange={(e) => setCustomerEmail(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Password: </label>
          <input
            className="form-control"
            type="password"
            name="password"
            value={customerPassword}
            placeholder="Enter Password"
            onChange={(e) => setCustomerPassword(e.target.value)}
          />
        </div>
        <div>
          <button type="submit" className="btn btn-primary btn-lg">
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default UpdateCustomerForm;
