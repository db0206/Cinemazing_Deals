import { createSlice } from "@reduxjs/toolkit";

const loginResponseSlice = createSlice({
  name: "loginResponse",
  initialState: {
    data: {
      token: null,
      email: null,
      name: null,
      clientType: null,
      expirationTime: null,
    },
  },
  reducers: {
    setData: (state, responseData) => {
      state.data.token = responseData.payload.token;
      state.data.email = responseData.payload.email;
      state.data.name = responseData.payload.name;
      state.data.clientType = responseData.payload.clientType;
      state.data.expirationTime = responseData.payload.expirationTime;
    },
  },
});

export const { setData } = loginResponseSlice.actions;

export default loginResponseSlice.reducer;
