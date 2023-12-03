import CreateAxiosInstance from "../utils/axiosInstance";

export const getAllDoctorsByDepts = async (deptId) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get(`/api/v1/doctor/department/${deptId}`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const getAllDoctors = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get(`/api/v1/doctor/getAll`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const getDoctorById = async (doctorId) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get(`/api/v1/doctor/${doctorId}`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const SearchDoctors = async (params) => {
  console.log(params);
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get("/api/v1/doctor/search", { params: params })
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const doctorDashboard = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .get("/api/v1/doctor/appointment/dashboard")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const updateAvailable = async (available) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .put(`/api/v1/doctor/available/${available}`)
    .then((response) => response.data.message)
    .catch((error) => {
      throw error;
    });
};
