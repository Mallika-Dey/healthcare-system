import React from "react";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBRow,
  MDBCol,
} from "mdb-react-ui-kit";
import { useForm } from "react-hook-form";
import MDBInputField from "../components/mdbInputField";
import MessageParser from "../components/messageParser";
import * as AuthService from "../service/authService";
import { useNavigate } from "react-router-dom";

function AdminRegistration() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    AuthService.registerAdmin(data)
      .then((response) => {
        localStorage.setItem("token", response.token);
        localStorage.setItem("role", response.role);
        localStorage.setItem("userId", response.userId);
        navigate("/");
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  return (
    <div style={{ marginTop: "100px" }}>
      <MDBContainer fluid>
        <MDBRow className="justify-content-center align-items-center m-5">
          <MDBCard>
            <MDBCardBody className="px-5">
              <h3 className="fw-bold mb-4 pb-2 pb-md-0 mb-md-5">
                Registration Form
              </h3>
              <form onSubmit={handleSubmit(onSubmit)} className="form">
                <MDBRow>
                  <MDBCol className="custom-label-wrapper">
                    <MDBInputField
                      name="email"
                      label="Email"
                      type="email"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="email"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol className="custom-label-wrapper">
                    <MDBInputField
                      name="password"
                      label="Password"
                      type="password"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="password"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBBtn className="mb-4" size="lg">
                  Submit
                </MDBBtn>
              </form>
            </MDBCardBody>
          </MDBCard>
        </MDBRow>
      </MDBContainer>
    </div>
  );
}

export default AdminRegistration;
