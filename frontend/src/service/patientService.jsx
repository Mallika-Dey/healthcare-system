import CreateAxiosInstance from "../utils/axiosInstance";

export const searchDonar = async (params) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .get("/api/v1/patient/profile/search", { params: params })
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const getProfile = async (userId) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .get(`/api/v1/patient/profile/getByUserId/${userId}`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const getHealth = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .get("/api/v1/patient/health/getByUserId/health-profile")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const createHealth = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .post("/api/v1/patient/health/createHealth", data)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const updateHealth = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .put("/api/v1/patient/health/updateHealth", data)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getAllPatient = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );
  return await axiosInstance
    .get("/api/v1/patient/profile/get/all")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};
