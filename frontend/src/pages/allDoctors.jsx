import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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
import { Pagination } from "antd";

import * as DoctorService from "../service/doctorService";
import { useForm, Controller } from "react-hook-form";
import DropdownField from "../components/dropdown";
import InputField from "../components/InputField";
import * as DeptService from "../service/adminService";
import DoctorCard from "../components/modals/doctor.component";
import ButtonComponent from "../components/buttons/buttonComponent";

function AllDoctors() {
  const { id } = useParams();
  const [doctors, setdoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(3);
  const [depts, setDepts] = useState([]);
  const [length, setLength] = useState(0);
  const userRole = localStorage.getItem("role");

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm();

  const fetchData = async () => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;

    DoctorService.getAllDoctors()
      .then((response) => {
        setLength(response.length);
        setdoctors(response.slice(startIndex, endIndex));
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });

    DeptService.getAllDepartments()
      .then((response) => {
        setDepts(response);
      })
      .catch((error) => {
        setError(error);
      });
  };

  useEffect(() => {
    fetchData();
  }, [currentPage, itemsPerPage]);

  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page);
    setItemsPerPage(pageSize);
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  const handleSearch = (data) => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;

    const queryParams = Object.fromEntries(
      Object.entries(data).filter(([_, value]) => value !== undefined)
    );

    DoctorService.SearchDoctors(queryParams)
      .then((response) => {
        setLength(response.length);
        setdoctors(response.slice(startIndex, endIndex));
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  };

  return (
    <div style={{ marginTop: "100px" }}>
      <h1 className="dept-doctors text-center">Find your Doctor</h1>
      <MDBContainer className="my-5">
        <MDBCard
          style={{
            boxShadow: "0 4px 8px 0 rgba(130, 106, 172, 0.5)",
          }}
        >
          <MDBRow className="g-0">
            <MDBCol md="6" className="mt-5">
              <form
                onSubmit={handleSubmit(handleSearch)}
                className="form"
                style={{ marginLeft: "20px", paddingLeft: "20px" }}
              >
                <div className="form-group pd-1">
                  <InputField
                    name="name"
                    className="form-control custom-border-color"
                    labelClassName="labelDesign"
                    type="text"
                    rules={{ required: false }}
                    placeholder="Name"
                    label="Search By Name"
                    register={register}
                    errors={errors}
                  />
                </div>

                <div className="form-group mt-3 custom-label-wrapper">
                  <Controller
                    name="department"
                    control={control}
                    render={({ field }) => (
                      <DropdownField
                        name="department"
                        label="Search By Department"
                        labelClassName="labelDesign"
                        field={field}
                        rules={{ required: false }}
                        options={depts.map((dept) => ({
                          label: dept.deptName,
                          value: dept.deptName,
                        }))}
                        register={register}
                        errors={errors}
                        style={{
                          borderColor: "#826AAC",
                          borderWidth: "2.5px",
                          width: "75%",
                        }}
                      />
                    )}
                  />
                </div>

                <div className="form-group mt-3 custom-label-wrapper">
                  <Controller
                    name="designation"
                    control={control}
                    render={({ field }) => (
                      <DropdownField
                        name="designation"
                        label="Search By Designation"
                        labelClassName="labelDesign"
                        field={field}
                        rules={{ required: false }}
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
                        style={{
                          borderColor: "#826AAC",
                          borderWidth: "2.5px",
                          width: "75%",
                        }}
                      />
                    )}
                  />
                </div>

                <div className="form-group mt-3">
                  <InputField
                    name="experience"
                    className="form-control custom-border-color"
                    labelClassName="labelDesign"
                    label="Search By Experience"
                    type="number"
                    rules={{ required: false }}
                    placeholder="Experience"
                    register={register}
                    errors={errors}
                  />
                </div>

                <div className="form-group mb-3 mt-3">
                  <InputField
                    name="specialization"
                    className="form-control custom-border-color"
                    label="Search By Specialization"
                    labelClassName="labelDesign"
                    type="text"
                    rules={{ required: false }}
                    placeholder="Specialization"
                    register={register}
                    errors={errors}
                  />
                </div>

                <ButtonComponent
                  buttonStyle="custom-border-color"
                  label="search"
                  customStyle={{
                    backgroundColor: "#826AAC",
                    borderWidth: "2.5px",
                    color: "#f7fcf9",
                    fontWeight: "bold",
                  }}
                />
              </form>
            </MDBCol>

            <MDBCol md="6">
              <MDBCardBody className="d-flex flex-column">
                {doctors &&
                  doctors.map((doctor, index) => (
                    <DoctorCard
                      key={index}
                      id={doctor.userId}
                      doctorName={doctor.name}
                      author={doctor.degree}
                      description={doctor.medicalName}
                      departmentName={doctor.departmentName}
                      medicalName={doctor.medicalName}
                      degree={doctor.degree}
                      designation={doctor.designation}
                      specialization={doctor.specialization}
                      yearOfExperience={doctor.yearOfExperience}
                      startTime={doctor.startTime}
                      endTime={doctor.endTime}
                      noOfDailyPatient={doctor.noOfDailyPatient}
                      available={doctor.available}
                      imageSrc={doctor.image}
                      className="my-4"
                      hoverable={true}
                      role={userRole}
                    />
                  ))}
              </MDBCardBody>
            </MDBCol>
          </MDBRow>
          <div className="d-flex justify-content-center">
            <Pagination
              current={currentPage}
              pageSize={itemsPerPage}
              total={length}
              onChange={handlePageChange}
              className="mb-3"
            />
          </div>
        </MDBCard>
      </MDBContainer>
    </div>
  );
}
export default AllDoctors;
