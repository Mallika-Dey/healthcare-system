import CreateAxiosInstance from "../utils/axiosInstance";

export const getAllDepartments = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/admin-service"
  );
  return await axiosInstance
    .get("/api/v1/admin/department/getAllDept")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};

export const addDept = async (data) => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/admin-service"
  );
  return await axiosInstance
    .post("/api/v1/admin/department/createDept", data)
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const adminDashboard = async () => {
  const axiosInstance = CreateAxiosInstance(
    "http://localhost:9090/admin-service"
  );
  return await axiosInstance
    .get("/api/v1/admin/dashboard/get")
    .then((response) => response.data.data)
    .catch((error) => {
      throw error;
    });
};
