import React from "react";
import AdminCard from "../components/cards/adminCard.component";
import { MDBRow, MDBCol } from "mdb-react-ui-kit";
import { Table, Divider } from "antd";

import { useEffect, useState } from "react";
import * as DoctorService from "../service/doctorService";
import * as AppointmentService from "../service/appointment";
import MessageParser from "../components/messageParser";

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
    title: "Start Time",
    dataIndex: "startTime",
  },
  {
    title: "Appointment Type",
    dataIndex: "appointmentType",
  },
];

const DoctorDashboard = () => {
  const [patientCount, setPatientCount] = useState();
  const [totalAppointment, setTotalAppointment] = useState();
  const [todayAppointment, setTodayAppointment] = useState();
  const [onlineAppointment, setOnlineAppointment] = useState();
  const [appointmentData, setAppointmentData] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    DoctorService.doctorDashboard()
      .then((response) => {
        console.log(response);
        setPatientCount(["Total Patients: ", <br />, response.totalPatient]);
        setTotalAppointment([
          "Total Appointments: ",
          <br />,
          response.totalAppointment,
        ]);
        setTodayAppointment([
          "Today's Appointments: ",
          <br />,
          response.todayAppointment,
        ]);
        setOnlineAppointment([
          "Online Appointments: ",
          <br />,
          response.totalOnlineAppointments,
        ]);
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  const fetchAppointment = async () => {
    AppointmentService.todayAppointment()
      .then((response) => {
        setAppointmentData(response);
      })
      .catch((error) => {
        MessageParser.error(error);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchData();
    fetchAppointment();
  }, []);

  const tableStyle = {
    border: "1px solid #e8e8e8",
    borderRadius: "5px",
  };

  return (
    <div style={{ marginTop: "100px", paddingLeft: "18px" }}>
      <MDBRow>
        <MDBCol md="3">
          <AdminCard
            iconName="fas fa-calendar-check"
            text={totalAppointment}
            size="2x"
            className="card-color-1"
          />
        </MDBCol>
        <MDBCol md="3">
          <AdminCard
            iconName="user"
            text={patientCount}
            size="2x"
            className="card-color-2"
          />
        </MDBCol>

        <MDBCol md="3">
          <AdminCard
            iconName="fas fa-calendar-check"
            text={todayAppointment}
            size="2x"
            className="card-color-3"
          />
        </MDBCol>
        <MDBCol md="3">
          <AdminCard
            iconName="fas fa-tv"
            text={onlineAppointment}
            size="2x"
            className="card-color-4"
          />
        </MDBCol>
      </MDBRow>

      <div className="mt-5">
        <Divider>Today's Appointment</Divider>
        <Table
          loading={loading}
          columns={columns}
          dataSource={appointmentData}
          className="pd-1 m-5"
          size="middle"
          style={tableStyle}
        />
      </div>
    </div>
  );
};

export default DoctorDashboard;
