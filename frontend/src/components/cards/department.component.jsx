import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  MDBCol,
  MDBCard,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
} from "mdb-react-ui-kit";

function Department({ id, title, hoverable, width, height, className, rooms }) {
  const navigate = useNavigate();
  const userRole = localStorage.getItem("role");
  const cardStyle = {
    width: width || 240,
    height: height || "auto",
    border: "1px solid #e8e8e8",
    borderRadius: "4px",
    boxShadow: hoverable ? "0 4px 8px rgba(0,0,0,0.2)" : "none",
    transition: "box-shadow 0.2s",
  };
  const cardClasses = `custom-card ${className || ""}`;

  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const handleClick = () => {
    navigate(`/deptinfo/${id}/${title}`);
  };
  return (
    <MDBCol md="4">
      <MDBCard style={{ backgroundColor: "#c2ede5", marginBottom: "50px" }}>
        <MDBCardBody onClick={handleClick}>
          <MDBCardTitle className="fw-bold">{title}</MDBCardTitle>
          <MDBCardText>
            Room - {rooms[0].roomNumber} to Room -{" "}
            {rooms[rooms.length - 1].roomNumber}
          </MDBCardText>
        </MDBCardBody>
      </MDBCard>
    </MDBCol>
  );
}
export default Department;
