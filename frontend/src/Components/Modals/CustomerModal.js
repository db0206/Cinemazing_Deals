import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import {
  setCustomerBody,
  setShowCustomerModal,
} from "../../redux/modalReducer";

const CustomerModal = () => {
  const show = useSelector((state) => state.modal.showCustomerModal);
  const body = useSelector((state) => state.modal.customerBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowCustomerModal(false));
    dispatch(
      setCustomerBody({
        customerId: null,
        firstName: null,
        lastName: null,
        email: null,
        password: null,
      })
    );
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title>Customer Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.customerId}</li>
        <li>
          Customer Name: {body.firstName} {body.lastName}
        </li>
        <li>Email: {body.email}</li>
        <li>Password: {body.password}</li>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CustomerModal;
