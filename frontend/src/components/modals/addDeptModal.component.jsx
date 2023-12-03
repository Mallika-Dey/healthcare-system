import React, { useState } from "react";
import { Modal } from "antd";
import { useForm, Controller } from "react-hook-form";
import * as DepartmentService from "../../service/adminService";
//import InputField from "../InputField";
import MessageParser from "../messageParser.jsx";
import MDBInputField from "../mdbInputField.jsx";

const AddDeptModal = ({ isAddModalOpen, setIsAddModalOpen }) => {
  //const [isModalOpen, setIsModalOpen] = useState(false);
  const [apiError, setApiError] = useState(null);

  const {
    control,
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const showModal = () => {
    setIsAddModalOpen(true);
  };

  const handleOk = async (data) => {
    DepartmentService.addDept(data)
      .then((response) => {
        MessageParser.success(response.message);
      })
      .catch((error) => MessageParser.error(error));

    console.log(data);

    setIsAddModalOpen(false);
  };

  const handleCancel = () => {
    setIsAddModalOpen(false);
  };

  return (
    <>
      <Modal
        title="Add New Department"
        okText="add"
        open={isAddModalOpen}
        onOk={handleSubmit(handleOk)}
        onCancel={handleCancel}
      >
        {apiError && <div className="alert alert-danger">{apiError}</div>}
        <form className="text-center mt-4 mb-2">
          <div className="custom-label-wrapper">
            <Controller
              name="deptName"
              control={control}
              render={({ field }) => (
                <MDBInputField
                  name="deptName"
                  type="text"
                  className="from-control"
                  label="Department Name"
                  rules={{ required: true, maxLength: 30 }}
                  register={register}
                  errors={errors}
                  {...field}
                />
              )}
            />
          </div>

          <div className="custom-label-wrapper">
            <Controller
              name="capacity"
              control={control}
              render={({ field }) => (
                <MDBInputField
                  name="capacity"
                  type="number"
                  className="from-control"
                  label="Number of room for this department"
                  rules={{ required: true, max: 99, min: 0 }}
                  register={register}
                  errors={errors}
                  {...field}
                />
              )}
            />
          </div>
        </form>
      </Modal>
    </>
  );
};
export default AddDeptModal;
