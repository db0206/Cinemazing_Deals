import React, { useEffect } from "react";
import axios from "axios";
import { Table, Container, Row, Col } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import CompanyModal from "../Modals/CompanyModal";
import {
  setCompanyBody,
  setShowCompanyModal,
  setShowUpdateCompanyModal,
} from "../../redux/modalReducer";
import { setCompanies } from "../../redux/usersReducer";
import UpdateCompanyModal from "../Modals/UpdateCompanyModal";

const CompanyList = () => {
  const token = localStorage.getItem("token");
  const companies = useSelector((state) => state.users.companies);
  const dispatch = useDispatch();

  useEffect(() => {

    if(token){
    axios
      .get("http://localhost:8080/admin/companies", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => dispatch(setCompanies(response.data)))
      .catch((error) => {
        alert("Failed to load companies", error);
      });
    }
  }, [token, dispatch]);

  const deleteCompany = (companyId) => {
    axios
      .delete(`http://localhost:8080/admin/company/${companyId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then(() => {
        console.log("Company deleted successfuly");
        dispatch(
          setCompanies(
            companies.filter((company) => company.companyId !== companyId)
          )
        );
      })
      .catch((error) => {
        console.error("Error deleting company: ", error);
        alert("Failed to delete company");
      });
  };

  const openModal = (companyId) => {
    dispatch(setShowCompanyModal(true));
    axios
      .get(`http://localhost:8080/admin/company/${companyId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        dispatch(setCompanyBody(response.data));
        console.log("Company: ", response.data);
      })
      .catch((error) => {
        console.error("Error loading company data: ", error);
        alert("Failed to load company data");
      });
  };

  const openUpdateModal = (companyId) => {
    dispatch(setShowUpdateCompanyModal(true));
    axios
      .get(`http://localhost:8080/admin/company/${companyId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        dispatch(setCompanyBody(response.data));
        console.log("Company: ", response.data);
      })
      .catch((error) => {
        console.error("Error loading company data: ", error);
        alert("Failed to load company data");
      });
  };

  return (
    <Container>
      <Row>
        <Col>
          <h2>Companies:</h2>
          <br />
          <Table striped bordered hover>
            <thead>
              <th>ID</th>
              <th>Company Name</th>
              <th>Info</th>
              <th>Update</th>
              <th>Delete</th>
            </thead>
            <tbody>
              {companies.map((company) => (
                <tr key={company.companyId}>
                  <td>{company.companyId}</td>
                  <td>{company.name}</td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-primary btn-lg"
                      onClick={() => openModal(company.companyId)}
                    >
                      Get Company Info
                    </button>
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-warning btn-lg"
                      onClick={() => openUpdateModal(company.companyId)}
                    >
                      Update Company
                    </button>
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-danger btn-lg"
                      onClick={() => deleteCompany(company.companyId)}
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
      <CompanyModal />
      <UpdateCompanyModal />
    </Container>
  );
};

export default CompanyList;
