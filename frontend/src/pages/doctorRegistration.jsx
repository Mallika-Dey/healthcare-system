import { useEffect, useState } from "react";
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
import * as DeptService from "../service/adminService";
import TimePickerField from "../components/timePicker.component";

function DoctorRegistration() {
  const navigate = useNavigate();
  const [department, setDepartment] = useState([]);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    AuthService.registerDoctor(data)
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

  const fetchDept = async () => {
    DeptService.getAllDepartments()
      .then((response) => {
        setDepartment(response);
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  useEffect(() => {
    fetchDept();
  }, []);

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
                      name="medicalName"
                      label="Medical College Name"
                      type="text"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="medicalName"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="mb-4 custom-label-wrapper">
                    <Controller
                      name="departmentName"
                      control={control}
                      render={({ field }) => (
                        <DropdownField
                          name="departmentName"
                          label="Department Name:"
                          field={field}
                          rules={{ required: true }}
                          options={department.map((item) => ({
                            label: item.deptName,
                            value: item.deptName,
                          }))}
                          register={register}
                          errors={errors}
                        />
                      )}
                    />
                  </MDBCol>

                  <MDBCol md="6" className="mb-4 custom-label-wrapper">
                    <Controller
                      name="degree"
                      control={control}
                      render={({ field }) => (
                        <DropdownField
                          name="degree"
                          label="Degree:"
                          field={field}
                          rules={{ required: true }}
                          options={[
                            { label: "BDS", value: "BDS" },
                            { label: "DO", value: "DO" },
                            { label: "MBBS", value: "MBBS" },
                            { label: "MD", value: "MD" },
                            { label: "MS", value: "MS" },
                          ]}
                          register={register}
                          errors={errors}
                        />
                      )}
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="mb-4 custom-label-wrapper">
                    <Controller
                      name="designation"
                      control={control}
                      render={({ field }) => (
                        <DropdownField
                          name="designation"
                          label="Designation:"
                          field={field}
                          rules={{ required: true }}
                          options={[
                            {
                              label: "Senior Consultant",
                              value: "SENIOR_CONSULTANT",
                            },
                            { label: "Professor", value: "PROFESSOR" },
                            {
                              label: "Associate Professor",
                              value: "ASSOCIATE_PROFESSOR",
                            },
                            { label: "Junior Doctor", value: "JUNIOR_DOCTOR" },
                            { label: "Resident", value: "RESIDENT" },
                            {
                              label: "Chief Physician",
                              value: "CHIEF_PHYSICIAN",
                            },
                            {
                              label: "Medical Officer",
                              value: "MEDICAL_OFFICER",
                            },
                            { label: "Surgeon", value: "SURGEON" },
                            {
                              label: "General Practitioner",
                              value: "GENERAL_PRACTITIONER",
                            },
                          ]}
                          register={register}
                          errors={errors}
                        />
                      )}
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper">
                    <Controller
                      name="startTime"
                      control={control}
                      rules={{ required: "The field is required." }}
                      render={({ field: { onChange, value } }) => (
                        <TimePickerField
                          style={{ height: "41px", marginBottom: "20px" }}
                          onChange={onChange}
                          value={value}
                          label="Start Time"
                          rules={{ require: true }}
                          error={errors.startTime}
                        />
                      )}
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="specialization"
                      label="Specialization"
                      type="text"
                      rules={{ required: true, maxLength: 30, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="specialization"
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="yearOfExperience"
                      label="Year Of Experience"
                      type="number"
                      rules={{ required: true, max: 50, min: 0 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="yearOfExperience"
                    />
                  </MDBCol>
                </MDBRow>

                <MDBRow>
                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="noOfDailyPatient"
                      label="No Of Daily Patient"
                      type="number"
                      rules={{ required: true, max: 20, min: 1 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="noOfDailyPatient"
                    />
                  </MDBCol>

                  <MDBCol md="6" className="custom-label-wrapper">
                    <MDBInputField
                      name="image"
                      label="Image"
                      type="text"
                      rules={{ required: true, maxLength: 200, minLength: 5 }}
                      className="form-control"
                      register={register}
                      errors={errors}
                      id="image"
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

export default DoctorRegistration;
