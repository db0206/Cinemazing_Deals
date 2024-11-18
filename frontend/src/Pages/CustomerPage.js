import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setCustomerBody, setShowCustomerModal } from "../redux/modalReducer";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import CustomerModal from "../Components/Modals/CustomerModal";
import CustomerCouponList from "../Components/Customer Components/CustomerCouponList";
import CouponStore from "../Components/Customer Components/CouponStore";
import { setData } from "../redux/loginResponseReducer";

const CustomerPage = () => {
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const loginData = useSelector((state) => state.loginResponse.data);
  const dispatch = useDispatch();
  const [page, setPage] = useState("main");

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

  const getCustomerDetails = () => {
    dispatch(setShowCustomerModal(true));
    axios
      .get("http://localhost:8080/customer/customer-details", {
        headers: {
          Authorization: loginData.token,
        },
      })
      .then((response) => {
        dispatch(setCustomerBody(response.data));
        console.log("Customer: ", response.data);
      })
      .catch((error) => {
        console.error("Error loading customer data: ", error);
        alert("Failed to load customer data");
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
            onClick={() => getCustomerDetails()}
          >
            Get Customer Details
          </button>
          <CustomerModal />
          <br />
          <br />
          {page === "main" && (
            <div className="text-center">
              <h2>Choose an Option:</h2>
              <button
                className="btn btn-warning btn-lg"
                onClick={() => setPage("store")}
              >
                Purchase a Coupon
              </button>
              <br />
              <br />
              <button
                className="btn btn-warning btn-lg"
                onClick={() => setPage("customer-coupons")}
              >
                View Your Coupons
              </button>
            </div>
          )}
          <br />
          {page === "store" && (
            <div className="text-center">
              <button
                className="btn btn-warning btn-lg"
                onClick={() => setPage("main")}
              >
                Back to Main Menu
              </button>
              <br />
              <br />
              <CouponStore />
            </div>
          )}
          {page === "customer-coupons" && (
            <div className="text-center">
              <button
                className="btn btn-warning btn-lg"
                onClick={() => setPage("main")}
              >
                Back to Main Menu
              </button>
              <br />
              <br />
              <CustomerCouponList />
            </div>
          )}
        </div>
      ) : (
        <div>
          <h1>No Authorization to View Customer Page</h1>
        </div>
      )}
    </div>
  );
};

export default CustomerPage;
