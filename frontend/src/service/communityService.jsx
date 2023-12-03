import CreateAxiosInstance from "../utils/axiosInstance";

export const getHospitalReviews = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/community-service"
  );
  return await axiosInstance
    .get("/api/v1/community/get/review/hospital")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const getDoctorReviews = async (doctorId) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/community-service"
  );
  return await axiosInstance
    .get(`/api/v1/community/get/review/doctor/${doctorId}`)
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const giveDoctorReview = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/community-service"
  );
  return await axiosInstance
    .post("/api/v1/community/review/doctor", data)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};
