import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addCompany } from "../../redux/usersReducer";

const AddCompanyForm = () => {
  const [showForm, setShowForm] = useState(false);
  const [companyName, setCompanyName] = useState("");
  const [companyEmail, setCompanyEmail] = useState("");
  const [companyPassword, setCompanyPassword] = useState("");

  const token = localStorage.getItem("token");
  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const company = {
      name: companyName,
      email: companyEmail,
      password: companyPassword,
    };

    axios
      .post("http://localhost:8080/admin/company", company, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        console.log("Company added: ", response.data);
        dispatch(addCompany(response.data));
        setCompanyName("");
        setCompanyEmail("");
        setCompanyPassword("");
      })
      .catch((error) => {
        console.error("Error adding company: ", error);
        alert("Error adding company");
      });
  };

  return (
    <div>
      <button
        type="button"
        className="btn btn-primary btn-lg"
        onClick={() => setShowForm(!showForm)}
      >
        {showForm ? "Hide Form" : "Add Company" }
      </button>
      {showForm && (
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Company Name: </label>
            <input
              className="form-control"
              type="text"
              name="name"
              value={companyName}
              placeholder="Enter Company Name"
              onChange={(e) => setCompanyName(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Email: </label>
            <input
              className="form-control"
              type="email"
              name="email"
              value={companyEmail}
              placeholder="Enter Company Email"
              onChange={(e) => setCompanyEmail(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Password: </label>
            <input
              className="form-control"
              type="password"
              name="password"
              value={companyPassword}
              placeholder="Enter Company Password"
              onChange={(e) => setCompanyPassword(e.target.value)}
            />
          </div>
          <div>
            <button type="submit" className="btn btn-primary btn-lg">
              Submit
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default AddCompanyForm;
