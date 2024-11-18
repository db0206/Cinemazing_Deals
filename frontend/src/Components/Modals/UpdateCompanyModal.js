import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { setCompanyBody, setShowUpdateCompanyModal } from "../../redux/modalReducer";
import UpdateCompanyForm from "../Admin Components/UpdateCompanyForm";

const UpdateCompanyModal = () => {
  const show = useSelector((state) => state.modal.showUpdateCompanyModal);
  const body = useSelector((state) => state.modal.companyBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowUpdateCompanyModal(false));
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
        <Modal.Title>Update Company</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.companyId}</li>
        <li>Company Name: {body.name}</li>
        <UpdateCompanyForm/>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default UpdateCompanyModal;
