import React from "react";
import {
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBCardText,
  MDBRow,
  MDBCol,
  MDBIcon,
} from "mdb-react-ui-kit";

const AdminCard = (props) => {
  return (
    <MDBCard className={`mt-3 ${props.className}`} style={{ width: "300px" }}>
      <MDBCardBody>
        <MDBRow>
          <MDBCol md="3" className="d-flex align-items-center">
            <MDBIcon fas icon={`${props.iconName}`} size={props.size} />
          </MDBCol>
          <MDBCol md="9">
            <MDBCardText>{props.text}</MDBCardText>
          </MDBCol>
        </MDBRow>
      </MDBCardBody>
    </MDBCard>
  );
};

export default AdminCard;
