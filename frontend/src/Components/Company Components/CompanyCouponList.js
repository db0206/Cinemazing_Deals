import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import {
  setCouponBody,
  setShowCouponModal,
  setShowUpdateCouponModal,
} from "../../redux/modalReducer";
import CouponModal from "../Modals/CouponModal";
import { setCompanyCoupons } from "../../redux/usersReducer";
import UpdateCouponModal from "../Modals/UpdateCouponModal";
import "../Coupon.css";

const Category = {
  EMPTY: "",
  NEW_GEAR: "NEW_GEAR",
  RENTAL_GEAR: "RENTAL_GEAR",
  PRODUCTION: "PRODUCTION",
  POST_PRODUCTION: "POST_PRODUCTION",
  MOVIE_THEATERS: "MOVIE_THEATERS",
  STREAMING: "STREAMING",
  DVD_AND_BLUERAY: "DVD_AND_BLUERAY",
};

const CompanyCouponList = () => {
  const token = localStorage.getItem("token");
  const coupons = useSelector((state) => state.users.companyCoupons);
  const [sort, setSort] = useState("none");
  const [couponCategory, setCouponCategory] = useState(Category.EMPTY);
  const [maxPrice, setMaxPrice] = useState(null);
  const dispatch = useDispatch();

  useEffect(() => {
    axios
      .get("http://localhost:8080/company/coupons", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => dispatch(setCompanyCoupons(response.data)))
      .catch((error) => {
        alert("Failed to load coupons", error);
      });
  }, [token, dispatch]);

  useEffect(() => {
    if (couponCategory !== Category.EMPTY) {
      axios
        .get(
          `http://localhost:8080/company/coupons-category?category=${couponCategory}`,
          {
            headers: {
              Authorization: token,
            },
          }
        )
        .then((response) => dispatch(setCompanyCoupons(response.data)))
        .catch((error) => {
          alert("Failed to load coupons", error);
        });
    } else {
      axios
        .get("http://localhost:8080/company/coupons", {
          headers: {
            Authorization: token,
          },
        })
        .then((response) => dispatch(setCompanyCoupons(response.data)))
        .catch((error) => {
          alert("Failed to load coupons", error);
        });
    }
  }, [couponCategory, token, dispatch]);

  useEffect(() => {
    if (sort === "none") {
      setCouponCategory(Category.EMPTY);
      setMaxPrice(null);
      axios
      .get("http://localhost:8080/company/coupons", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => dispatch(setCompanyCoupons(response.data)))
      .catch((error) => {
        alert("Failed to load coupons", error);
      });
    }
  }, [setCouponCategory, setMaxPrice, sort, dispatch, token]);

  const handleCategoryChange = (e) => {
    const value = e.target.value;
    if (Object.values(Category).includes(value)) {
      setCouponCategory(value);
    } else {
      console.error("Invalid category type selected");
    }
  };

  const getCouponDetails = (coupon) => {
    dispatch(setShowCouponModal(true));
    dispatch(setCouponBody(coupon));
  };

  const updateCoupon = (coupon) => {
    dispatch(setShowUpdateCouponModal(true));
    dispatch(setCouponBody(coupon));
  };

  const callCouponsByPrice = (maxPrice) => {
    axios
      .get(`http://localhost:8080/company/coupons-price?price=${maxPrice}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => dispatch(setCompanyCoupons(response.data)))
      .catch((error) => {
        alert("Failed to load coupons", error);
      });
  };

  const deleteCoupon = (couponId) => {
    axios
      .delete(`http://localhost:8080/company/coupon/${couponId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then(() => {
        console.log("coupon deleted successfully");
        dispatch(
          setCompanyCoupons(
            coupons.filter((coupon) => coupon.couponId !== couponId)
          )
        );
      })
      .catch((error) => {
        console.error("Error deleting coupon: ", error);
        alert("Failed to delete coupon");
      });
  };

  return (
    <div>
      <h2 className="text-center">Coupon List</h2>

      <select
        className="form-control text-center"
        name="sort"
        value={sort}
        onChange={(e) => setSort(e.target.value)}
      >
        <option value="none">Sort By:</option>
        <option value="category">Category</option>
        <option value="max-price">Max Price</option>
      </select>

      <br />

      {sort === "category" && (
        <div className="mb-3">
          <label className="form-label">Category Type: </label>
          <select
            className="form-control text-center"
            name="category"
            value={couponCategory}
            onChange={handleCategoryChange}
          >
            <option value={Category.EMPTY}>Select Category Type: </option>
            <option value={Category.NEW_GEAR}>New Gear</option>
            <option value={Category.RENTAL_GEAR}>Rental Gear</option>
            <option value={Category.PRODUCTION}>Production</option>
            <option value={Category.POST_PRODUCTION}>Post Production</option>
            <option value={Category.MOVIE_THEATERS}>Movie Theaters</option>
            <option value={Category.STREAMING}>Streaming</option>
            <option value={Category.DVD_AND_BLUERAY}>DVD & Bluray</option>
          </select>
        </div>
      )}

      {sort === "max-price" && (
        <div className="mb-3">
          <label className="form-label">Set Max Price: </label>
          <input
            className="form-control"
            type="number"
            name="maxPrice"
            value={maxPrice}
            onChange={(e) => setMaxPrice(e.target.value)}
          ></input>

          <button
            className="btn btn-primary"
            onClick={() => callCouponsByPrice(maxPrice)}
          >
            Set
          </button>
        </div>
      )}

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
              className="btn btn-warning"
              onClick={() => updateCoupon(coupon)}
            >
              Update
            </button>
            <button
              className="btn btn-danger"
              onClick={() => deleteCoupon(coupon.couponId)}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
      <CouponModal />
      <UpdateCouponModal />
    </div>
  );
};

export default CompanyCouponList;
