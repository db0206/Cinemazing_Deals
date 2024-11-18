import React from "react";
import { useNavigate } from "react-router-dom";
import CompanyCouponList from "../Components/Company Components/CompanyCouponList";
import AddCouponForm from "../Components/Company Components/AddCouponForm";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { setShowCompanyModal, setCompanyBody } from "../redux/modalReducer";
import CompanyModal from "../Components/Modals/CompanyModal";
import { setData } from "../redux/loginResponseReducer";

const CompanyPage = () => {
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const loginData = useSelector((state) => state.loginResponse.data);
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

  const getCompanyDetails = () => {
    dispatch(setShowCompanyModal(true));
    axios
      .get("http://localhost:8080/company/company-details", {
        headers: {
          Authorization: loginData.token,
        },
      })
      .then((response) => {
        dispatch(setCompanyBody(response.data));
        console.log("Company: ", response.data);
      })
      .catch((error) => {
        console.error("Error loading company data: ", error);
        alert("Failed to load company data");
      });
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
          <h1>Welcome {loginData.name}</h1>
          <button
            className="btn btn-primary"
            onClick={() => getCompanyDetails()}
          >
            Get Company Details
          </button>
          <br />
          <CompanyCouponList />
          <br />
          <AddCouponForm />
          <CompanyModal />
        </div>
      ) : (
        <div>
          <h1>No Authorization to View Company Page</h1>
        </div>
      )}
    </div>
  );
};

export default CompanyPage;
