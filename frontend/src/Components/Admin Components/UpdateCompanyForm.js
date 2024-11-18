import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";


const UpdateCompanyForm = () => {

  const [companyEmail, setCompanyEmail] = useState("");
  const [companyPassword, setCompanyPassword] = useState("");
  

  const token = localStorage.getItem("token");
  const body = useSelector((state) => state.modal.companyBody);
  

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedCompany = {
        companyId: body.companyId,
        name: body.name,
        email: companyEmail,
        password: companyPassword,
    };

    axios.put("http://localhost:8080/admin/company", updatedCompany, {
        headers: {
          Authorization: token,
        },
      })
      .then((response)=>{
        console.log('Company updated successfuly: ', response.data);
        alert('Company updated successfully')
        setCompanyEmail("");
        setCompanyPassword("");
      })
      .catch((error)=>{
        console.error('Failed to update company: ', error);
        
      })
  };

  return (
    <div>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Update Email: </label>
            <input
              className="form-control"
              type="email"
              name="email"
              value={companyEmail}
              placeholder="Enter New Company Email"
              onChange={(e) => setCompanyEmail(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Update Password: </label>
            <input
              className="form-control"
              type="password"
              name="password"
              value={companyPassword}
              placeholder="Enter New Company Password"
              onChange={(e) => setCompanyPassword(e.target.value)}
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

export default UpdateCompanyForm;
