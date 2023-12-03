import { useState } from "react";
import { Avatar, Card, Rate, Popconfirm, Space } from "antd";
import { useNavigate } from "react-router-dom";
import ButtonComponent from "../buttons/buttonComponent";

import { Col } from "antd";
const { Meta } = Card;

function Doctor(props) {
  const navigate = useNavigate();
  const userRole = localStorage.getItem("role");

  const cardStyle = {
    width: props.width || 270,
    height: props.height || "auto",
    border: "1px solid #e8e8e8",
    borderRadius: "4px",
    boxShadow: props.hoverable ? "0 4px 8px rgba(0,0,0,0.2)" : "none",
    transition: "box-shadow 0.2s",
  };
  const cardClasses = `custom-card ${props.className || ""}`;

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleClick = () => {
    navigate("/appointment");
  };

  const handleImageClick = () => {
    const doctorId = props.id;
    navigate(`/doctorinfo/${doctorId}`);
  };
  return (
    <Col span={7}>
      <Card
        className={cardClasses}
        style={cardStyle}
        hoverable={props.hoverable}
        cover={
          <img
            alt={props.doctorName}
            src={props.imageSrc}
            style={{ width: "100%", height: "300px" }}
            onClick={handleImageClick}
          />
        }
        actions={
          userRole === "PATIENT"
            ? [
                <ButtonComponent
                  key="appointment"
                  onClick={handleClick}
                  label="Appointment"
                  buttonStyle="btn-primary"
                />,
              ]
            : null
        }
      >
        <Card.Meta
          title={
            <span style={{ fontSize: "20px", fontWeight: "bold" }}>
              {props.doctorName}, {props.degree}
            </span>
          }
          description={
            <span style={{ fontSize: "16px" }}>{props.designation}</span>
          }
          style={{ textAlign: "center" }}
        />
      </Card>
    </Col>
  );
}
export default Doctor;
