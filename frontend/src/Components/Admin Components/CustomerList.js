import React, { useEffect } from "react";
import axios from "axios";
import { Table, Container, Row, Col } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import CustomerModal from "../Modals/CustomerModal";
import {
  setCustomerBody,
  setShowCustomerModal,
  setShowUpdateCustomerModal,
} from "../../redux/modalReducer";
import { setCustomers } from "../../redux/usersReducer";
import UpdateCustomerModal from "../Modals/UpdateCustomerModal";

const CustomerList = () => {
  const dispatch = useDispatch();
  const customers = useSelector((state) => state.users.customers);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if(token){
    axios
      .get("http://localhost:8080/admin/customers", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => dispatch(setCustomers(response.data)))
      .catch((error) => {
        alert("Failed to load customers", error);
      });
    }
  }, [token, dispatch]);

  const deleteCustomer = (customerId) => {
    axios
      .delete(`http://localhost:8080/admin/customer/${customerId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then(() => {
        console.log("Customer deleted successfuly");
        dispatch(setCustomers(
          customers.filter((customer) => customer.customerId !== customerId)
        ));
      })
      .catch((error) => {
        console.error("Error deleting customer: ", error);
        alert("Failed to delete customer");
      });
  };

  const openModal = (customerId) => {
    dispatch(setShowCustomerModal(true));
    axios
      .get(`http://localhost:8080/admin/customer/${customerId}`, {
        headers: {
          Authorization: token,
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

  const openUpdateModal = (customerId) => {
    dispatch(setShowUpdateCustomerModal(true));
    axios
      .get(`http://localhost:8080/admin/customer/${customerId}`, {
        headers: {
          Authorization: token,
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
    <Container>
      <Row>
        <Col>
          <h2>Customers:</h2>
          <br />
          <Table striped bordered hover>
            <thead>
              <th>ID</th>
              <th>Customer Name</th>
              <th>Info</th>
              <th>Update</th>
              <th>Delete</th>
            </thead>
            <tbody>
              {customers.map((customer) => (
                <tr key={customer.customerId}>
                  <td>{customer.customerId}</td>
                  <td>
                    {customer.firstName} {customer.lastName}
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-primary btn-lg"
                      onClick={() => openModal(customer.customerId)}
                    >
                      Get Customer Info
                    </button>
                  </td>
                  <td>
                    <button type="button" className="btn btn-warning btn-lg" onClick={() => openUpdateModal(customer.customerId)}> 
                      Update Customer
                    </button>
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-danger btn-lg"
                      onClick={() => deleteCustomer(customer.customerId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
      <CustomerModal />
      <UpdateCustomerModal/>
    </Container>
  );
};

export default CustomerList;
