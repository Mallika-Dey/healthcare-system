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
import { useForm } from "react-hook-form";
import MDBInputField from "../components/mdbInputField";
import * as AuthService from "../service/authService";
import MessageParser from "../components/messageParser";

function Login() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    try {
      const response = await AuthService.loginUser(data);
      localStorage.setItem("token", response.token);
      localStorage.setItem("role", response.role);
      localStorage.setItem("userId", response.userId);
      navigate("/");
    } catch (error) {
      MessageParser.error(error);
    }
  };

  return (
    <div style={{ marginTop: "100px" }}>
      <MDBContainer className="my-5">
        <MDBCard>
          <MDBRow className="g-0">
            <MDBCol md="6">
              <MDBCardImage
                src="https://media.istockphoto.com/id/1310702105/vector/lady-doctor.jpg?s=612x612&w=0&k=20&c=QXh_Dpc9UaDRlll0ddD9Z4vPlMzRzw2fezhCN_KH3SI="
                alt="login form"
                className="rounded-start w-100"
              />
            </MDBCol>

            <MDBCol md="6">
              <MDBCardBody className="d-flex flex-column">
                <div className="d-flex flex-row justify-content-center align-items-center mt-2 mb-3">
                  <MDBIcon
                    fas
                    icon="briefcase-medical fa-3x me-3"
                    style={{ color: "#ff6219" }}
                  />
                  <span className="h1 fw-bold mb-1">Sign In</span>
                </div>

                <form onSubmit={handleSubmit(onSubmit)} className="form">
                  <div className="form-group custom-label-wrapper">
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
                  </div>

                  <div className="form-group custom-label-wrapper">
                    <MDBInputField
                      name="password"
                      label="Password"
                      type="password"
                      className="form-control"
                      rules={{ required: true, maxLength: 18, minLength: 8 }}
                      register={register}
                      errors={errors}
                      id="password"
                    />
                  </div>

                  <MDBBtn className="mb-4 mt-2 px-5" color="dark" size="lg">
                    Login
                  </MDBBtn>
                </form>
                <a className="small text-muted" href="#!">
                  Forgot password?
                </a>
                <p className="mb-5 pb-lg-2" style={{ color: "#393f81" }}>
                  Don't have an account?{" "}
                  <a href="#!" style={{ color: "#393f81" }}>
                    Register here
                  </a>
                </p>

                <div className="d-flex flex-row justify-content-start">
                  <a href="#!" className="small text-muted me-1">
                    Terms of use.
                  </a>
                  <a href="#!" className="small text-muted">
                    Privacy policy
                  </a>
                </div>
              </MDBCardBody>
            </MDBCol>
          </MDBRow>
        </MDBCard>
      </MDBContainer>
    </div>
  );
}

export default Login;
