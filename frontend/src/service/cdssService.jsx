import CreateAxiosInstance from "../utils/axiosInstance";

export const getPatientProgress = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/cdss-service"
  );
  return await axiosInstance
    .get("/api/v1/cdss/get/patient/progress")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const startConsult = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/cdss-service"
  );
  return await axiosInstance
    .get("/api/v1/cdss/start")
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const continueConsult = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/cdss-service"
  );
  return await axiosInstance
    .post("/api/v1/cdss/respond", data)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getRecommendation = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/cdss-service"
  );
  return await axiosInstance
    .get("/api/v1/cdss/get/patient/recommendation")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};
