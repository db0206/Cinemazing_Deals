import React from "react";
import CompanyList from "../Components/Admin Components/CompanyList";
import CustomerList from "../Components/Admin Components/CustomerList";
import AddCompanyForm from "../Components/Admin Components/AddCompanyForm";
import AddCustomerForm from "../Components/Admin Components/AddCustomerForm";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setData } from "../redux/loginResponseReducer";

const AdminPage = () => {
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleLogout = () => {
    localStorage.removeItem("token");
    dispatch(
      setData({
        token: null,
        email: null,
        name: null,
        clientType: null,
        expirationTime: null,
      })
    );
    navigate("/");
  };

  return (
    <div>
      {token ? (
        <div className="App-header">
          <button
            className="btn btn-primary btn-lg position-absolute top-0 start-0"
            onClick={handleLogout}
          >
            Logout
          </button>
          <h1>Welcome Admin</h1>
          <CompanyList />
          <AddCompanyForm />
          <br />
          <CustomerList />
          <AddCustomerForm />
        </div>
      ) : (
        <div>
          <h1>No Authorization to View Admin Page</h1>
        </div>
      )}
    </div>
  );
};

export default AdminPage;
