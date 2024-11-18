import { configureStore } from "@reduxjs/toolkit";
import loginResponseReducer from "./loginResponseReducer";
import modalReducer from "./modalReducer";
import usersReducer from "./usersReducer";

const store = configureStore({
  reducer: {
    loginResponse: loginResponseReducer,
    modal: modalReducer,
    users: usersReducer,
  },
});

export default store;
