import React from "react";
import { MDBRow, MDBCol } from "mdb-react-ui-kit";
import { Table, Divider } from "antd";

import { useEffect, useState } from "react";
import * as AdminService from "../service/adminService";
import MessageParser from "../components/messageParser";
import * as ReviewService from "../service/communityService";
import AdminCard from "../components/cards/adminCard.component";
import * as PatientService from "../service/patientService";

const columns = [
  {
    title: "Image",
    dataIndex: "imageUrl",
    render: (text) => (
      <img
        src={text}
        alt="Patient"
        className="rounded-circle"
        style={{ width: "40px", height: "40px" }}
      />
    ),
  },
  {
    title: "Name",
    dataIndex: "name",
  },
  {
    title: "Address",
    dataIndex: "address",
  },
  {
    title: "Mobile Number",
    dataIndex: "mobileNo",
  },
  {
    title: "Bloodgroup",
    dataIndex: "bloodGroup",
  },
];

const AdminDashboard = () => {
  const [patientCount, setPatientCount] = useState();
  const [totalDonor, setDonorCount] = useState();
  const [totalDepartment, setDepartmentCount] = useState();
  const [totalDoctor, setDoctorCount] = useState();
  const [reviews, setReview] = useState([]);
  const [patients, setPatient] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    AdminService.adminDashboard()
      .then((response) => {
        setPatientCount(["Total Patients ", <br />, response.patientCount]);
        setDonorCount(["Total Donors ", <br />, response.totalDonor]);
        setDepartmentCount([
          "Total Departments ",
          <br />,
          response.totalDepartment,
        ]);
        setDoctorCount(["Total Doctors ", <br />, response.totalDoctor]);
      })
      .catch((error) => {
        MessageParser.error(error);
      });

    PatientService.getAllPatient()
      .then((response) => {
        setPatient(response);
      })
      .catch((error) => {
        MessageParser.error(error);
      })
      .finally(() => {
        setLoading(false);
      });

    // ReviewService.getHospitalReviews()
    //   .then((response) => setReview(response))
    //   .catch((error) => MessageParser.error(error));
  };

  const tableStyle = {
    border: "1px solid #e8e8e8",
    borderRadius: "5px",
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div style={{ marginTop: "100px", paddingLeft: "16px" }}>
      <Divider>
        <h1>Admin Dashboard</h1>
      </Divider>
      <MDBRow>
        <MDBCol md="3">
          <AdminCard
            iconName="user-md"
            text={totalDoctor}
            size="3x"
            className="dept-doctors fw-bold"
          />
        </MDBCol>
        <MDBCol md="3">
          <AdminCard
            iconName="home"
            text={totalDepartment}
            size="3x"
            className="admin-color fw-bold"
          />
        </MDBCol>
        {/* </MDBRow>
      <MDBRow> */}
        <MDBCol md="3">
          <AdminCard
            iconName="user-friends"
            text={patientCount}
            size="3x"
            className="dept-doctors fw-bold"
          />
        </MDBCol>
        <MDBCol md="2">
          <AdminCard
            iconName="burn"
            text={totalDonor}
            size="3x"
            className="admin-color fw-bold"
          />
        </MDBCol>
      </MDBRow>
      <div className="mt-5">
        <Divider className="my-5 fw-bold">Patient data</Divider>
        <Table
          loading={loading}
          columns={columns}
          dataSource={patients}
          className="pd-1 m-5 text-center"
          size="middle"
          style={tableStyle}
        />
      </div>
    </div>
  );
};

export default AdminDashboard;
