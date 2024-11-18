import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import {
  setCustomerBody,
  setShowUpdateCustomerModal,
} from "../../redux/modalReducer";
import UpdateCustomerForm from "../Admin Components/UpdateCustomerForm";

const UpdateCustomerModal = () => {
  const show = useSelector((state) => state.modal.showUpdateCustomerModal);
  const body = useSelector((state) => state.modal.customerBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowUpdateCustomerModal(false));
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
        <Modal.Title>Update Customer</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.customerId}</li>
        <UpdateCustomerForm />
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default UpdateCustomerModal;
