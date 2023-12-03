import CreateAxiosInstance from "../utils/axiosInstance";

export const loginUser = async (loginData) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/security-service"
  );
  return await axiosInstance
    .post("/api/v1/auth/login", loginData)
    .then((response) => response.data.data)
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

export const registerPatient = async (registerData) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/patient-service"
  );

  return await axiosInstance
    .post("/api/v1/patient/register", registerData)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const registerAdmin = async (registerData) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/admin-service"
  );
  return await axiosInstance
    .post("/api/v1/admin/register", registerData)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const registerDoctor = async (registerData) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/doctor-service"
  );
  return await axiosInstance
    .post("/api/v1/doctor/register", registerData)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};
