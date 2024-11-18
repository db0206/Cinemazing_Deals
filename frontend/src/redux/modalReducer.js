import { createSlice } from "@reduxjs/toolkit";

const modalSlice = createSlice({
  name: "modal",
  initialState: {
    showCompanyModal: false,
    showCustomerModal: false,
    showCouponModal: false,
    showUpdateCompanyModal: false,
    showUpdateCustomerModal: false,
    showUpdateCouponModal: false,
    companyBody: {
      companyId: null,
      name: null,
      email: null,
      password: null,
    },
    customerBody: {
      customerId: null,
      firstName: null,
      lastName: null,
      email: null,
      password: null,
    },
    couponBody: {
      couponId: null,
      company: null,
      category: null,
      title: null,
      description: null,
      startDate: null,
      endDate: null,
      amount: null,
      price: null,
      image: null,
    },
  },
  reducers: {
    setShowCompanyModal: (state, showMode) => {
      state.showCompanyModal = showMode.payload;
    },
    setShowCustomerModal: (state, showMode) => {
      state.showCustomerModal = showMode.payload;
    },
    setShowCouponModal: (state, showMode) => {
      state.showCouponModal = showMode.payload;
    },
    setShowUpdateCompanyModal: (state, showMode) => {
      state.showUpdateCompanyModal = showMode.payload;
    },
    setShowUpdateCustomerModal: (state, showMode) => {
      state.showUpdateCustomerModal = showMode.payload;
    },
    setShowUpdateCouponModal: (state, showMode) => {
      state.showUpdateCouponModal = showMode.payload;
    },
    setCompanyBody: (state, data) => {
      state.companyBody.companyId = data.payload.companyId;
      state.companyBody.name = data.payload.name;
      state.companyBody.email = data.payload.email;
      state.companyBody.password = data.payload.password;
    },
    setCustomerBody: (state, data) => {
      state.customerBody.customerId = data.payload.customerId;
      state.customerBody.firstName = data.payload.firstName;
      state.customerBody.lastName = data.payload.lastName;
      state.customerBody.email = data.payload.email;
      state.customerBody.password = data.payload.password;
    },
    setCouponBody: (state, data) => {
      state.couponBody.couponId = data.payload.couponId;
      state.couponBody.company = data.payload.company;
      state.couponBody.category = data.payload.category;
      state.couponBody.title = data.payload.title;
      state.couponBody.description = data.payload.description;
      state.couponBody.startDate = data.payload.startDate;
      state.couponBody.endDate = data.payload.endDate;
      state.couponBody.amount = data.payload.amount;
      state.couponBody.price = data.payload.price;
      state.couponBody.image = data.payload.image;
    },
  },
});

export const {
  setShowCompanyModal,
  setShowCustomerModal,
  setShowCouponModal,
  setCompanyBody,
  setCustomerBody,
  setCouponBody,
  setShowUpdateCompanyModal,
  setShowUpdateCustomerModal,
  setShowUpdateCouponModal,
} = modalSlice.actions;

export default modalSlice.reducer;
