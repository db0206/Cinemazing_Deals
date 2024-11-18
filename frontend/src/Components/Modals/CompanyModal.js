import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { setCompanyBody, setShowCompanyModal } from "../../redux/modalReducer";

const CompanyModal = () => {
  const show = useSelector((state) => state.modal.showCompanyModal);
  const body = useSelector((state) => state.modal.companyBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowCompanyModal(false));
    dispatch(
      setCompanyBody({
        companyId: null,
        name: null,
        email: null,
        password: null,
      })
    );
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title>Company Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.companyId}</li>
        <li>Company Name: {body.name}</li>
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

export default CompanyModal;
