import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { setCouponBody, setShowCouponModal } from "../../redux/modalReducer";

const CouponModal = () => {
  const show = useSelector((state) => state.modal.showCouponModal);
  const body = useSelector((state) => state.modal.couponBody);
  const dispatch = useDispatch();
  const handleClose = () => {
    dispatch(setShowCouponModal(false));
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
        <Modal.Title>Coupon Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <li>ID: {body.couponId}</li>
        <li>Title: {body.title}</li>
        <li>Category: {body.category}</li>
        <li>description: {body.description}</li>
        <li>Start Date: {body.startDate}</li>
        <li>End Date: {body.endDate}</li>
        <li>Amount: {body.amount}</li>
        <li>Price: {body.price}</li>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CouponModal;
