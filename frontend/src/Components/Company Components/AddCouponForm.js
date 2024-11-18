import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addCompanyCoupon } from "../../redux/usersReducer";

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

const AddCouponForm = () => {
  const [showForm, setShowForm] = useState(false);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState(Category.EMPTY);
  const [startDate, setStartDate] = useState(new Date().toISOString().split("T")[0]);
  const [endDate, setEndDate] = useState(new Date().toISOString().split("T")[0]);
  const [amount, setAmount] = useState(0);
  const [price, setPrice] = useState(0);

  const token = localStorage.getItem("token");
  const dispatch = useDispatch();

  const handleCategoryChange = (e) => {
    const value = e.target.value;
    if (Object.values(Category).includes(value)) {
      setCategory(value);
    } else {
      console.error("Invalid category type selected");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (category === Category.EMPTY) {
      alert("Please select a valid category");
      return;
    }

    const coupon = {
      category: category,
      title: title,
      description: description,
      startDate: startDate,
      endDate: endDate,
      amount: amount,
      price: price,
      image: null,
    };

    axios
      .post("http://localhost:8080/company/coupon", coupon, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        console.log("Coupon added: ", response.data);
        dispatch(addCompanyCoupon(response.data));
        setTitle("");
        setDescription("");
        setAmount(0);
        setPrice(0);
        setStartDate(new Date().toISOString().split("T")[0]);
        setEndDate(new Date().toISOString().split("T")[0]);
        setCategory(Category.EMPTY);
      })
      .catch((error) => {
        console.error("Error adding coupon: ", error);
        alert("Error adding coupon");
      });
  };

  return (
    <div className="text-center">
      <button
        type="button"
        className="btn btn-primary btn-lg"
        onClick={() => setShowForm(!showForm)}
      >
        {showForm ? "Hide Form" : "Add Coupon"}
      </button>
      {showForm && (
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Title: </label>
            <input
              className="form-control"
              type="text"
              name="title"
              value={title}
              placeholder="Title"
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Description: </label>
            <input
              className="form-control"
              type="text"
              name="description"
              value={description}
              placeholder="Description"
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Category Type: </label>
            <select
              className="form-control"
              name="category"
              value={category}
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
          <div className="mb-3">
            <label className="form-label">Start Date: </label>
            <input
              className="form-control"
              type="date"
              name="startDate"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">End Date: </label>
            <input
              className="form-control"
              type="date"
              name="endDate"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Amount: </label>
            <input
              className="form-control"
              type="number"
              name="amount"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Price: </label>
            <input
              className="form-control"
              type="number"
              name="price"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </div>
          <button type="submit" className="btn btn-primary btn-lg">
            Submit
          </button>
        </form>
      )}
    </div>
  );
};

export default AddCouponForm;
