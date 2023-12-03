import React, { useEffect, useState } from "react";
import {
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBBtn,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBCardTitle,
  MDBCardText,
  MDBTextArea,
} from "mdb-react-ui-kit";
import { useNavigate } from "react-router-dom";
import * as DoctorService from "../service/doctorService";
import MessageParser from "../components/messageParser";

function DoctorProfile() {
  const [doctor, setDoctor] = useState(null);
  const [active, setActive] = useState(false);
  const navigate = useNavigate();

  const userRole = localStorage.getItem("role");

  const fetchDoctor = async () => {
    DoctorService.getDoctorById(localStorage.getItem("userId"))
      .then((response) => {
        setDoctor(response);
        setActive(response.available);
      })
      .catch((error) => MessageParser.error(error));
  };

  const handleCall = (event) => {
    navigate(`/call?roomID=${localStorage.getItem("userId")}`);
  };

  useEffect(() => {
    fetchDoctor();
  }, []);

  const onChange = (checked) => {
    DoctorService.updateAvailable(!active)
      .then((response) => {
        setActive(!active);
        MessageParser.success(response);
      })
      .catch((error) => MessageParser.error(error));
  };

  if (!doctor) return <div>Loading...</div>;

  return (
    <div style={{ marginTop: "10em" }}>
      <MDBContainer>
        <MDBRow className="my-5">
          <MDBCard style={{ backgroundColor: "#E3F2FD" }}>
            <MDBCardBody>
              <MDBRow>
                <MDBCol
                  md="4"
                  className="d-flex flex-column align-items-center"
                >
                  <div className="d-flex justify-content-center">
                    <MDBCardImage
                      src={doctor.image}
                      alt={doctor.name}
                      style={{ maxHeight: "25em", maxWidth: "20em" }}
                    />
                  </div>
                  <div className="d-flex justify-content-center mt-3">
                    <MDBBtn
                      color={active ? "success" : "warning"}
                      className="ms-2 p-2"
                      onClick={onChange}
                    >
                      {active ? "Online" : "Offline"}
                    </MDBBtn>

                    <MDBBtn
                      color={active ? "success" : "secondary"}
                      disabled={!active}
                      className="ms-2 p-2"
                      onClick={handleCall}
                    >
                      <i className="fas fa-phone" /> Join Call
                    </MDBBtn>
                  </div>
                </MDBCol>

                <MDBCol md="8" className="doctor-details pt-5">
                  <MDBCardTitle className="text-style">
                    {doctor.name}, {doctor.degree}
                  </MDBCardTitle>
                  <MDBCardText>
                    <strong>Department:</strong> {doctor.departmentName}
                  </MDBCardText>
                  <MDBCardText>
                    <strong>Medical Name:</strong> {doctor.medicalName}
                  </MDBCardText>
                  <MDBCardText>
                    <strong>Designation:</strong> {doctor.designation}
                  </MDBCardText>
                  <MDBCardText>
                    <strong>Specialization:</strong> {doctor.specialization}
                  </MDBCardText>
                  <MDBCardText>
                    <strong>Experience:</strong> {doctor.yearOfExperience} years
                  </MDBCardText>
                </MDBCol>
              </MDBRow>
            </MDBCardBody>
          </MDBCard>
        </MDBRow>
      </MDBContainer>
    </div>
  );
}

export default DoctorProfile;
