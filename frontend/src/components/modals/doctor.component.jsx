import React from "react";
import { Link } from "react-router-dom";

const DoctorCard = (doctor) => {
  return (
    <div className="card border shadow-sm p-3 mb-3">
      <div className="row">
        <div className="col-md-4 d-flex flex-column justify-content-between">
          <img
            src={doctor.imageSrc}
            alt={doctor.doctorName}
            className="img-fluid mb-3"
          />
          <Link
            to={`/doctorinfo/${doctor.id}`}
            className="btn btn-outline-primary"
          >
            Profile
          </Link>
        </div>
        <div className="col-md-8">
          <h5 className="font-weight-bold">{doctor.doctorName}</h5>
          <div className="pb-3">
            <span className="text-muted">
              {doctor.designation}, {doctor.degree}
            </span>
          </div>
          <div>
            <span className="text-muted">
              Department: {doctor.departmentName}
            </span>
            <br />
            <span className="text-muted">
              Experience: {doctor.yearOfExperience} years
            </span>
          </div>
          {doctor.role === "PATIENT" && (
            <Link
              to={`/appointment/${doctor.id}`}
              className="btn btn-primary mt-3"
            >
              Appointment Now
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default DoctorCard;
