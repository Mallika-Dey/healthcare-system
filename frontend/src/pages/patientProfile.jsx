import { useEffect, useState } from "react";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBRow,
  MDBCol,
  MDBCardTitle,
  MDBCardText,
} from "mdb-react-ui-kit";

import MessageParser from "../components/messageParser";
import * as PatientService from "../service/patientService";
import { useNavigate } from "react-router-dom";

function PatientProfile() {
  const [profile, setProfile] = useState(null);
  const [health, setHealth] = useState(null);
  const navigate = useNavigate();

  const fetchProfile = async () => {
    PatientService.getProfile(localStorage.getItem("userId"))
      .then((response) => {
        setProfile(response);
      })
      .catch((error) => MessageParser.error(error));

    PatientService.getHealth()
      .then((response) => {
        setHealth(response);
      })
      .catch((error) => MessageParser.error(error));
  };

  const addHealthData = (event) => {
    navigate(`/add-health-data`);
  };

  const updateHealthData = (event) => {
    navigate(`/update-health-data`);
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  if (!profile) return <div>Loading...</div>;

  return (
    <div style={{ marginTop: "6em" }}>
      <MDBContainer>
        <MDBRow className="my-5">
          <MDBCard style={{ backgroundColor: "#e8ebed" }}>
            <MDBCardBody>
              <MDBRow>
                <MDBCol
                  md="4"
                  className="d-flex flex-column align-items-center mt-5 pt-5"
                >
                  <div className="d-flex justify-content-center">
                    <MDBCardImage
                      src={profile.imageUrl}
                      alt={profile.name}
                      style={{
                        maxHeight: "14em",
                        maxWidth: "18em",
                        border: "5px solid black",
                        backgroundColor: "white",
                      }}
                    />
                  </div>
                  <div className="mt-4 px-5 mx-5">
                    <MDBCardTitle className="text-center">
                      <strong>{profile.name}</strong>
                    </MDBCardTitle>
                    <MDBCardText className="text-center">
                      <i className="fas fa-location-dot"></i> {profile.address}
                    </MDBCardText>
                    <MDBCardText>
                      <i className="fas fa-phone"></i> {profile.mobileNo}
                    </MDBCardText>
                  </div>

                  {health && (
                    <div
                      style={{
                        marginTop: "-20px",
                        fontSize: "52px",
                        fontFamily: "Roboto Serif",
                        fontWeight: "bold",
                      }}
                    >
                      <MDBBtn
                        onClick={updateHealthData}
                        className="btn btn-success"
                      >
                        Update Health
                      </MDBBtn>
                    </div>
                  )}
                </MDBCol>

                <MDBCol md="8" className="patient-details pt-5">
                  <MDBRow>
                    <MDBCardTitle
                      style={{ marginLeft: "2.4em", marginBottom: ".6em" }}
                      className="text-style"
                    >
                      Health Profile
                    </MDBCardTitle>
                    {health ? (
                      <>
                        <MDBCol>
                          <MDBCardText>
                            <strong>Age: </strong>
                            {health.age}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Weight: </strong>
                            {health.weight} kg
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Height: </strong>
                            {health.height} cm
                          </MDBCardText>
                          <MDBCardText>
                            <strong>BMI: </strong>
                            {health.bmi}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>BMR: </strong>
                            {health.bmr}
                          </MDBCardText>

                          <MDBCardText>
                            <strong>Chest Pain: </strong>
                            {health.chestPain ? "Yes" : "No"}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Sugar Level: </strong>
                            {health.sugarLevel}
                          </MDBCardText>

                          <MDBCardText>
                            <strong>Gender: </strong>
                            {health.gender}
                          </MDBCardText>

                          <MDBCardText>
                            <strong>Smoker: </strong>
                            {health.smoke ? "Yes" : "No"}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Sinusitis: </strong>
                            {health.sinusitis ? "Yes" : "No"}
                          </MDBCardText>
                        </MDBCol>

                        <MDBCol md="8">
                          <MDBCardText>
                            <strong>Blood Group: </strong>
                            {health.bloodGroup}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>High Cholesterol: </strong>
                            {health.highCholesterol ? "Yes" : "No"}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Blood Pressure: </strong>
                            {health.bloodPressure}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Goal Type: </strong>
                            {health.goalType}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Activity Level: </strong>
                            {health.activityLevel}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Previous Stroke: </strong>
                            {health.previousStroke ? "Yes" : "No"}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>
                              Family History of Cardiovascular Disease:{" "}
                            </strong>
                            {health.familyHistoryCardiovascularDisease
                              ? "Yes"
                              : "No"}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Fasting Blood Glucose Level: </strong>
                            {health.fastingBloodGlucoseLevel}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Alcohol Consumption: </strong>
                            {health.alcoholConsumption}
                          </MDBCardText>
                          <MDBCardText>
                            <strong>Thirst Level: </strong>
                            {health.thirstLevel}
                          </MDBCardText>
                        </MDBCol>
                      </>
                    ) : (
                      <div style={{ marginLeft: "12em", marginBottom: ".6em" }}>
                        <p>
                          <i className="fas fa-times-circle" /> No data
                          available
                        </p>
                        <MDBBtn
                          onClick={addHealthData}
                          style={{ marginLeft: "14px" }}
                        >
                          Add Health
                        </MDBBtn>
                      </div>
                    )}
                  </MDBRow>
                </MDBCol>
              </MDBRow>
            </MDBCardBody>
          </MDBCard>
        </MDBRow>
      </MDBContainer>
    </div>
  );
}

export default PatientProfile;
