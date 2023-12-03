import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
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
} from "mdb-react-ui-kit";
import { Empty } from "antd";
import * as DoctorService from "../service/doctorService";
import MessageParser from "../components/messageParser";
import MDBInputField from "../components/mdbInputField";
import * as CommunityService from "../service/communityService";
import { ReviewModel } from "../models/review";

function DoctorDetailsPage() {
  const { doctorId } = useParams();
  const [doctor, setDoctor] = useState(null);
  const [reviews, setReview] = useState([]);
  const navigate = useNavigate();

  const userRole = localStorage.getItem("role");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const fetchData = async () => {
    DoctorService.getDoctorById(doctorId)
      .then((response) => setDoctor(response))
      .catch((error) => MessageParser.error(error));

    CommunityService.getDoctorReviews(doctorId)
      .then((response) => setReview(response))
      .catch((error) => MessageParser.error(error));
  };

  const handleCall = (event) => {
    //navigate(`/call?roomID=${doctorId}`);
    window.open(`/call?roomID=${doctorId}`, "_blank");
  };

  const onSubmit = (data) => {
    CommunityService.giveDoctorReview(
      new ReviewModel({ doctorId: doctorId, reviewText: data.reviewText })
    )
      .then((response) => MessageParser.success(response.message))
      .catch((error) => MessageParser.error(error));
  };

  const getAppointment = () => {
    navigate(`/appointment/${doctorId}`);
  };

  useEffect(() => {
    fetchData();
  }, []);

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
                  {userRole === "PATIENT" && (
                    <div className="d-flex justify-content-center mt-3">
                      <MDBBtn color="primary" onClick={getAppointment}>
                        Get <br />
                        Appointment
                      </MDBBtn>
                      <MDBBtn
                        color={doctor.available ? "success" : "secondary"}
                        disabled={!doctor.available}
                        className="ms-2"
                        onClick={handleCall}
                      >
                        <i className="fas fa-phone" /> Call
                      </MDBBtn>
                    </div>
                  )}
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

          <MDBCard className="mt-4">
            <MDBCardBody className="custom-label-wrapper">
              <MDBCardTitle className="mb-2 text-center">Reviews</MDBCardTitle>
              {reviews && reviews.length > 0 ? (
                reviews.map((review, index) => (
                  <div className="facebook-comment" key={index}>
                    <div className="comment-image">
                      <img src={review.imageUrl} alt="Reviewer" />
                    </div>
                    <div className="comment-content">
                      <p className="comment-author">{review.name}</p>
                      <p className="comment-text">{review.reviewText}</p>
                    </div>
                  </div>
                ))
              ) : (
                <div className="text-center py-10">
                  <Empty
                    description={
                      <span className="text-gray-600 text-lg">
                        No data available.
                      </span>
                    }
                  />
                </div>
              )}
            </MDBCardBody>
          </MDBCard>

          {userRole === "PATIENT" && (
            <MDBCard className="mt-4">
              <MDBCardBody className="custom-label-wrapper">
                <MDBCardTitle className="mb-3">Add Your Review</MDBCardTitle>
                <form onSubmit={handleSubmit(onSubmit)} className="form">
                  <div className="form-group custom-label-wrapper">
                    <MDBInputField
                      name="reviewText"
                      label="Add your review"
                      type="text"
                      rules={{ required: true, maxLength: 300, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="text"
                    />
                  </div>

                  <MDBBtn type="submit" className="mt-2" color="secondary">
                    Submit Review
                  </MDBBtn>
                </form>
              </MDBCardBody>
            </MDBCard>
          )}
        </MDBRow>
      </MDBContainer>
    </div>
  );
}

export default DoctorDetailsPage;
