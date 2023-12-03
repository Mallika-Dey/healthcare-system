import React from "react";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBRow,
  MDBCol,
} from "mdb-react-ui-kit";
import { useForm, Controller } from "react-hook-form";
import MDBInputField from "../components/mdbInputField";
import DropdownField from "../components/dropdown";
import MessageParser from "../components/messageParser";
import * as AuthService from "../service/authService";
import { useNavigate } from "react-router-dom";
import DatePickerField from "../components/datePicker.component";

function Registration() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    AuthService.registerPatient(data)
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
            <MDBCardBody className="px-4">
              <h3 className="fw-bold mb-4 pb-2 pb-md-0 mb-md-5">
                Registration Form
              </h3>
              <form onSubmit={handleSubmit(onSubmit)} className="form">
                <MDBRow>
                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="name"
                      label="Name"
                      id="name"
                      type="text"
                      className="form-control"
                      rules={{ required: true, maxLength: 30, minLength: 3 }}
                      register={register}
                      errors={errors}
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper">
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
                  <MDBCol md="6" className="custom-label-wrapper mb-3">
                    <Controller
                      name="bloodGroup"
                      control={control}
                      render={({ field }) => (
                        <DropdownField
                          name="bloodGroup"
                          label="Blood Group"
                          field={field}
                          rules={{ required: false }}
                          options={[
                            { label: "A+", value: "A_POSITIVE" },
                            { label: "A-", value: "A_NEGATIVE" },
                            { label: "B+", value: "B_POSITIVE" },
                            { label: "B-", value: "B_NEGATIVE" },
                            { label: "AB+", value: "AB_POSITIVE" },
                            { label: "AB-", value: "AB_NEGATIVE" },
                            { label: "O+", value: "O_POSITIVE" },
                            { label: "O-", value: "O_NEGATIVE" },
                          ]}
                          register={register}
                          errors={errors}
                        />
                      )}
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper mb-3">
                    <Controller
                      name="dateOfBirth"
                      control={control}
                      rules={{ required: "The field is required." }}
                      render={({ field: { onChange, value } }) => (
                        <DatePickerField
                          style={{ height: "41px" }}
                          labelClass="dateOfBirth"
                          label="Date Of Birth"
                          onChange={onChange}
                          value={value}
                          rules={{ require: true }}
                          error={errors.dateOfBirth}
                        />
                      )}
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="address"
                      label="Address"
                      type="text"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="address"
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="mobileNo"
                      label="Phone Number"
                      type="text"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="mobileNo"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="custom-label-wrapper">
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

                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="imageUrl"
                      label="Image"
                      type="text"
                      rules={{ required: true, maxLength: 300, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="imageUrl"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="mb-4 custom-label-wrapper">
                    <Controller
                      name="interestedInBloodDonate"
                      control={control}
                      render={({ field }) => (
                        <DropdownField
                          name="interestedInBloodDonate"
                          label="Interested in blood donate:"
                          field={field}
                          rules={{ required: false }}
                          options={[
                            { label: "YES", value: true },
                            { label: "NO", value: false },
                          ]}
                          register={register}
                          errors={errors}
                        />
                      )}
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

export default Registration;
