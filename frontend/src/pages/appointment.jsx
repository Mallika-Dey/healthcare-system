import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBBtn,
  MDBInput,
  MDBIcon,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
} from "mdb-react-ui-kit";
import { useForm, Controller } from "react-hook-form";
import DropdownField from "../components/dropdown";
import { AppointmentModel } from "../models/appointment";
import * as AppointmentService from "../service/appointment";
import MessageParser from "../components/messageParser";

function Appointment() {
  const { doctorId } = useParams();
  const [selectedDate, setSelectedDate] = useState(null);
  const [showRadioButtons, setShowRadioButtons] = useState(false);
  const [slots, setSlots] = useState(false);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm();

  const handleDateChange = (event) => {
    setSelectedDate(event.target.value);

    AppointmentService.getAvailableSlots(doctorId, event.target.value)
      .then((response) => {
        setSlots(response);
      })
      .catch((error) => {
        MessageParser.error(error);
      });

    setShowRadioButtons(true);
  };

  const onSubmit = (data) => {
    const newAppointment = new AppointmentModel({
      ...data,
      doctorUserId: doctorId,
      startTime: data.startTime,
      appointmentDate: selectedDate,
      appointmentType: data.appointmentType,
    });

    AppointmentService.takeAppointment(newAppointment)
      .then((response) => {
        console.log(response.message);
        MessageParser.success(response.message);
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  return (
    <div style={{ marginTop: "10em" }}>
      <MDBContainer className="my-5">
        <MDBCard>
          <MDBRow className="g-0">
            <MDBCol md="6">
              <MDBCardImage
                src="https://ibpf.org/wp-content/uploads/2016/09/doctors-appointment-mental-health.png"
                alt="login form"
                className="rounded-start w-100 h-100"
              />
            </MDBCol>

            <MDBCol md="6" style={{ backgroundColor: "#f8f9fa" }}>
              <MDBCardBody className="d-flex flex-column">
                <div className="d-flex flex-row justify-content-center align-items-center mt-2 mb-5">
                  <MDBIcon
                    fas
                    icon="fas fa-calendar-check fa-3x me-3"
                    style={{ color: "#4981eb" }}
                  />
                  {/* <i class="fas fa-calendar-check"></i> */}
                  <span className="h1 fw-bold mt-2">Appointment</span>
                </div>

                <form onSubmit={handleSubmit(onSubmit)} className="form">
                  <div className="form-group custom-label-wrapper">
                    <MDBInput
                      label="Choose a date"
                      type="date"
                      name="appointmentDate"
                      id="appointmentDate"
                      required={true}
                      onChange={handleDateChange}
                      className="mb-4"
                    />
                  </div>

                  {showRadioButtons && slots && (
                    <>
                      <div className="form-group mb-3 custom-label-wrapper">
                        <Controller
                          name="startTime"
                          control={control}
                          render={({ field }) => (
                            <DropdownField
                              name="startTime"
                              label="Appointment Time"
                              field={field}
                              rules={{ required: true }}
                              options={slots.map((slot) => ({
                                label: slot.startTime + " - " + slot.endTime,
                                value: slot.startTime,
                              }))}
                              register={register}
                              errors={errors}
                            />
                          )}
                        />
                      </div>
                      <div className="form-group custom-label-wrapper">
                        <Controller
                          name="appointmentType"
                          control={control}
                          render={({ field }) => (
                            <DropdownField
                              name="appointmentType"
                              label="Appointment Type"
                              field={field}
                              rules={{ required: true }}
                              options={[
                                { label: "IN PERSON", value: "IN_PERSON" },
                                {
                                  label: "TELEMEDICINE",
                                  value: "TELEMEDICINE",
                                },
                              ]}
                              register={register}
                              errors={errors}
                            />
                          )}
                        />
                      </div>

                      <MDBBtn className="mt-2 px-3" color="primary" size="lg">
                        Submit
                      </MDBBtn>
                    </>
                  )}
                </form>
              </MDBCardBody>
            </MDBCol>
          </MDBRow>
        </MDBCard>
      </MDBContainer>
    </div>
  );
}

export default Appointment;
