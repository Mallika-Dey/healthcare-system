export class AppointmentModel {
  constructor({
    doctorUserId = "",
    startTime = "",
    appointmentDate = "",
    appointmentType = "",
  }) {
    this.doctorUserId = doctorUserId;
    this.startTime = startTime;
    this.appointmentDate = appointmentDate;
    this.appointmentType = appointmentType;
  }
}
