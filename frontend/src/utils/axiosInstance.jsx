import axios from "axios";
// import { useNavigate } from "react-router-dom";

const CreateAxiosInstance = (apiUrl) => {
  // const navigate = useNavigate();
  const instance = axios.create({
    baseURL: apiUrl,
    headers: {
      "Content-Type": "application/json",
    },
  });

  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("token");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response.status === 403) {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        localStorage.removeItem("userId");
        // navigate("/login");
      }
      return Promise.reject(error);
    }
  );

  return instance;
};

export default CreateAxiosInstance;
