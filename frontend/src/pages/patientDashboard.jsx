import {
  MDBContainer,
  MDBCard,
  MDBRow,
  MDBCol,
  MDBCardText,
  MDBCardBody,
} from "mdb-react-ui-kit";
import { Table, Divider } from "antd";

import { useEffect, useState } from "react";
import * as ProgressService from "../service/cdssService";
import MessageParser from "../components/messageParser";
import Graph from "../components/graph/graph.component";
import * as AppointmentService from "../service/appointment";

const columns = [
  {
    title: "Image",
    dataIndex: "imageUrl",
    render: (text) => (
      <img
        src={text}
        alt="Doctor"
        className="rounded-circle"
        style={{ width: "40px", height: "40px" }}
      />
    ),
  },
  {
    title: "Doctor Name",
    dataIndex: "doctorName",
  },
  {
    title: "Start Time",
    dataIndex: "startTime",
  },
  {
    title: "End Time",
    dataIndex: "endTime",
  },
  {
    title: "Appointment Date",
    dataIndex: "appointmentDate",
  },
  {
    title: "Appointment Type",
    dataIndex: "appointmentType",
  },
];

const PatientDashboard = () => {
  const [progress, setProgress] = useState(null);
  const [upcomingAppointment, setAppointment] = useState([]);
  const [recommendation, setRecommendation] = useState();
  const [loading, setLoading] = useState(true);

  const getProgress = () => {
    ProgressService.getPatientProgress()
      .then((response) => {
        setProgress(response);
      })
      .catch((error) => MessageParser.error(error));
  };

  const getUpcomingAppointment = () => {
    AppointmentService.upcomingAppointment()
      .then((response) => {
        setAppointment(response);
      })
      .catch((error) => MessageParser.error(error))
      .finally(() => {
        setLoading(false);
      });
  };

  const getRecommendation = () => {
    ProgressService.getRecommendation()
      .then((response) => {
        setRecommendation(response);
      })
      .catch((error) => MessageParser.error(error));
  };

  useEffect(() => {
    getProgress();
    getUpcomingAppointment();
    getRecommendation();
  }, []);

  const tableStyle = {
    border: "1px solid #e8e8e8",
    borderRadius: "5px",
  };

  return (
    <div
      style={{
        marginTop: "100px",
        paddingLeft: "18px",
      }}
    >
      <MDBContainer className="my-4">
        <MDBCard style={{ backgroundColor: "#f5fcfc" }}>
          <MDBRow className="g-0">
            <MDBCol>
              <div className="mt-5">
                <Divider>
                  <h3>Upcoming Appointments</h3>
                </Divider>
                <Table
                  loading={loading}
                  columns={columns}
                  dataSource={upcomingAppointment}
                  className="pd-1 m-5"
                  size="middle"
                  style={tableStyle}
                />
              </div>
            </MDBCol>
          </MDBRow>

          <MDBRow className="g-0 mb-4">
            <MDBCol>
              <Divider>BMI Progress</Divider>
              <Graph progressData={progress} className="mx-1" />
            </MDBCol>

            <MDBCol style={{ marginLeft: "2em", marginRight: "1em" }}>
              <Divider>Recommendation</Divider>
              {recommendation && (
                <>
                  <MDBCard>
                    <MDBCardBody>
                      <MDBCardText>
                        <strong>Preferred BMI:</strong>{" "}
                        {recommendation.preferredBMI.toFixed(1)}
                      </MDBCardText>
                      <MDBCardText>
                        <strong>Health Risk:</strong>{" "}
                        {recommendation.healthRisk}
                      </MDBCardText>
                      <MDBCardText>
                        <strong>Recommendation:</strong>{" "}
                        {recommendation.recommendation}
                      </MDBCardText>
                    </MDBCardBody>
                  </MDBCard>
                </>
              )}
            </MDBCol>
          </MDBRow>
        </MDBCard>
      </MDBContainer>
    </div>
  );
};

export default PatientDashboard;
