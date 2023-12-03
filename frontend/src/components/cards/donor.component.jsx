import React from "react";
import { Link } from "react-router-dom";

const DonorCard = (donor) => {
  return (
    <div className="col-md-3 mb-3">
      <div
        className="card border shadow-sm"
        style={{ maxHeight: "400px", maxWidth: "200px" }}
      >
        <img
          src={donor.imageSrc}
          alt={donor.name}
          className="card-img-top"
          style={{ maxHeight: "350px", maxWidth: "200px" }}
        />
        <div className="card-body">
          <h5 className="card-title font-weight-bold">{donor.name}</h5>
          <p className="card-text text-muted">{donor.address}</p>
          <Link
            to={`/doctor-profile/${donor.id}`}
            className="btn btn-outline-warning"
          >
            Contact
          </Link>
        </div>
      </div>
    </div>
  );
};

export default DonorCard;
