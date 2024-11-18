import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { setCouponBody, setShowUpdateCouponModal } from "../../redux/modalReducer";
import UpdateCouponForm from "../Company Components/UpdateCouponForm";

const UpdateCouponModal = () => {
  const show = useSelector((state) => state.modal.showUpdateCouponModal);
  const body = useSelector((state) => state.modal.couponBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowUpdateCouponModal(false));
    dispatch(
      setCouponBody({
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
      })
    );
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title>Update Coupon</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.couponId}</li>
        <UpdateCouponForm/>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default UpdateCouponModal;
