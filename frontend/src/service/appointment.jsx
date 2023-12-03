import CreateAxiosInstance from "../utils/axiosInstance";

export const getAvailableSlots = async (userId, date) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get(`/api/v1/doctor/appointment/doctor/${userId}/freeSlot/${date}`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const takeAppointment = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .post("/api/v1/doctor/appointment/take", data)
    .then((response) => response.data)
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

export const todayAppointment = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get("/api/v1/doctor/appointment/get")
    .then((response) => response.data.data)
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

export const upcomingAppointment = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get("/api/v1/doctor/appointment/get/patient/upcoming")
    .then((response) => response.data.data)
    .catch((error) => {
      console.log(error);
      throw error;
    });
};
