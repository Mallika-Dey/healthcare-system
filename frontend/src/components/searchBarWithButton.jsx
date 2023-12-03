import React from "react";
import ButtonComponent from "./buttons/buttonComponent";

const SearchBarWithButton = ({ onSearchChange, userRole, openAddModal }) => {
  return (
    <div className="d-flex justify-content-between align-items-center mb-4">
      <div className="d-flex justify-content-center w-100">
        <div className="input-group" style={{ maxWidth: "400px" }}>
          <span className="input-group-text">
            <i className="fas fa-search"></i>
          </span>
          <input
            type="text"
            className="form-control"
            placeholder="Search departments"
            onChange={onSearchChange}
          />
        </div>
      </div>
      {userRole === "ADMIN" && (
        <ButtonComponent
          onClick={openAddModal}
          label="Add Department"
          buttonStyle="btn-primary mx-4"
          width="18em"
          height="40px"
        />
      )}
    </div>
  );
};

export default SearchBarWithButton;
