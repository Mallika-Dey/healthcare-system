import React from "react";
import {
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBRow,
  MDBCol,
  MDBIcon,
} from "mdb-react-ui-kit";
import * as BloodService from "../service/patientService";
import MessageParser from "../components/messageParser";
import { useEffect, useState } from "react";
import { BloodDonateModel } from "../models/bloodDonate";
import DonorCard from "../components/cards/donor.component";

function BloodBank() {
  const [bloodGroup, setSearchText] = useState(null);
  const [doners, setDoner] = useState(null);

  const fetchDoner = async () => {
    const bloodGRP = new BloodDonateModel(bloodGroup);
    BloodService.searchDonar(bloodGRP)
      .then((response) => {
        console.log(response);
        setDoner(response);
      })
      .catch((error) => MessageParser.error(error));
  };

  useEffect(() => {
    const timeOut = setTimeout(() => {
      fetchDoner();
    }, 1500);

    return () => clearTimeout(timeOut);
  }, [bloodGroup]);

  return (
    <div style={{ marginTop: "100px" }}>
      <MDBContainer className="my-5">
        <MDBCard>
          <MDBRow className="g-0">
            <MDBCol md="6">
              <MDBCardBody className="d-flex flex-column">
                <div className="d-flex flex-row justify-content-center align-items-center mt-2 mb-3">
                  <MDBIcon
                    fas
                    icon="burn fa-3x me-3"
                    style={{ color: "#ff6219" }}
                  />
                  <span className="h1 fw-bold mb-1">Blood Bank</span>
                </div>
              </MDBCardBody>
            </MDBCol>

            <MDBCol md="6">
              <MDBCardBody className="d-flex flex-column">
                <div className="d-flex justify-content-center mb-4">
                  <div
                    className="input-group w-50"
                    style={{ maxWidth: "300px" }}
                  >
                    <select
                      className="form-select"
                      onChange={(e) => {
                        setSearchText(e.target.value);
                      }}
                    >
                      <option value="">Select an option</option>
                      <option value="A_POSITIVE">A+</option>
                      <option value="A_NEGATIVE">A-</option>
                      <option value="B_POSITIVE">B+</option>
                      <option value="B_NEGATIVE">B-</option>
                      <option value="AB_POSITIVE">AB+</option>
                      <option value="AB_NEGATIVE">AB-</option>
                      <option value="O_POSITIVE">O+</option>
                      <option value="O_NEGATIVE">O-</option>
                    </select>
                  </div>
                </div>
              </MDBCardBody>
            </MDBCol>
            <MDBCol>
              <MDBCardBody className="d-flex flex-column">
                <div className="row row-cols-1 row-cols-md-4">
                  {doners &&
                    doners.map((donor, index) => (
                      <DonorCard
                        key={index}
                        id={donor.userId}
                        name={donor.name}
                        bloodGroup={donor.bloodGroup}
                        address={donor.address}
                        imageSrc={donor.imageUrl}
                        className="my-4"
                        hoverable={true}
                      />
                    ))}
                </div>
              </MDBCardBody>
            </MDBCol>
          </MDBRow>
        </MDBCard>
      </MDBContainer>
    </div>
  );
}

export default BloodBank;
