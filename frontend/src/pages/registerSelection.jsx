import React from "react";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBRow,
  MDBCol,
  MDBIcon,
  MDBInput,
} from "mdb-react-ui-kit";
import { useNavigate } from "react-router-dom";

function RegisterSelect() {
  const navigate = useNavigate();

  const handleAdmin = async () => {
    navigate("/admin-register");
  };

  const handlePatient = async () => {
    navigate("/patient-register");
  };

  const handleDoctor = async () => {
    navigate("/doctor-register");
  };

  return (
    <div
      style={{ marginTop: "100px", marginLeft: "20em", marginRight: "25em" }}
    >
      <MDBContainer className="m-5">
        <MDBCard
          style={{
            textAlign: "center",
            backgroundColor: "#fce8ae",
          }}
        >
          <h1 className="mt-4">Select Registration</h1>
          <MDBRow>
            <MDBCardBody className="d-flex flex-column">
              <MDBBtn
                className="mb-2 mt-2 px-5 mx-5"
                color="dark"
                size="lg"
                style={{ width: "30em" }}
                onClick={handleAdmin}
              >
                Register as an admin
              </MDBBtn>
            </MDBCardBody>

            <MDBCardBody className="d-flex flex-column">
              <MDBBtn
                className="px-5 mx-5"
                color="dark"
                size="lg"
                style={{ width: "30em" }}
                onClick={handlePatient}
              >
                Register as a patient
              </MDBBtn>
            </MDBCardBody>

            <MDBCardBody className="d-flex flex-column">
              <MDBBtn
                className="mb-4 px-5 mx-5"
                color="dark"
                size="lg"
                style={{ width: "30em" }}
                onClick={handleDoctor}
              >
                Register as a doctor
              </MDBBtn>
            </MDBCardBody>
          </MDBRow>
        </MDBCard>
      </MDBContainer>
    </div>
  );
}

export default RegisterSelect;
