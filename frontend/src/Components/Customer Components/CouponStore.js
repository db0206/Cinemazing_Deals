import axios from "axios";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setAllCoupons } from "../../redux/usersReducer";
import CouponModal from "../Modals/CouponModal";
import { setCouponBody, setShowCouponModal } from "../../redux/modalReducer";

const CouponStore = () => {
  const coupons = useSelector((state) => state.users.allCoupons);
  const token = localStorage.getItem("token");
  const dispatch = useDispatch();

  useEffect(() => {
    axios
      .get("http://localhost:8080/customer/all-coupons", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        dispatch(setAllCoupons(response.data));
      })
      .catch((error) => {
        alert("Failed to load coupons", error);
      });
  }, [token, dispatch]);

  const getCouponDetails = (coupon) => {
    dispatch(setShowCouponModal(true));
    dispatch(setCouponBody(coupon));
  };

  const purchaseCoupon = (couponId) => {
    axios.post(`http://localhost:8080/customer/purchase-coupon/${couponId}`, null, {
        headers: {
          Authorization: token,
        },
      })
      .then((response)=> {
        alert('Coupon purchased successfully');
      })
      .catch((error) => {
        alert("Failed to purchase coupon", error);
      })
  };

  return (
    <div>
      <div className="coupon-container">
        {coupons.map((coupon) => (
          <div key={coupon.couponId} className="coupon">
            <h3 className="text-danger">{coupon.title}</h3>
            <p className="text-muted">Coupon ID: {coupon.couponId}</p>
            <button
              className="btn btn-primary"
              onClick={() => getCouponDetails(coupon)}
            >
              Full Details
            </button>
            <button
              className="btn btn-danger"
              onClick={() => purchaseCoupon(coupon.couponId)}
            >
              Purchase Coupon
            </button>
          </div>
        ))}
      </div>
      <CouponModal />
    </div>
  );
};

export default CouponStore;
