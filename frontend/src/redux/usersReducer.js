import { createSlice } from "@reduxjs/toolkit";

const usersSlice = createSlice({
  name: "users",
  initialState: {
    companies: [],
    customers: [],
    companyCoupons: [],
    customerCoupons: [],
    allCoupons: [],
  },
  reducers: {
    setCompanies: (state, responseData) => {
      state.companies = responseData.payload;
    },
    addCompany: (state, action) => {
      state.companies.push(action.payload);
    },
    setCustomers: (state, responseData) => {
      state.customers = responseData.payload;
    },
    addCustomer: (state, action) => {
      state.customers.push(action.payload);
    },
    setCompanyCoupons: (state, responseData) => {
      state.companyCoupons = responseData.payload;
    },
    addCompanyCoupon: (state, action) => {
      state.companyCoupons.push(action.payload);
    },
    updateCompanyCoupon: (state, action) => {
      const updatedCoupon = action.payload;
      state.companyCoupons = state.companyCoupons.map((coupon) =>
        coupon.couponId === updatedCoupon.couponId ? updatedCoupon : coupon
      );
    },
    setCustomerCoupons: (state, responseData) => {
      state.customerCoupons = responseData.payload;
    },
    setAllCoupons: (state, responseData) => {
      state.allCoupons = responseData.payload;
    },
  },
});

export const {
  setCompanies,
  setCustomers,
  addCompany,
  addCustomer,
  setCompanyCoupons,
  addCompanyCoupon,
  updateCompanyCoupon,
  setCustomerCoupons,
  setAllCoupons,
} = usersSlice.actions;

export default usersSlice.reducer;
