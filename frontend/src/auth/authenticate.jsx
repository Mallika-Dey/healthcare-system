import { Navigate, Outlet } from "react-router-dom";

const Authenticate = () => {
  const token = localStorage.getItem("token");

  return <div>{token ? <Outlet /> : <Navigate to="/login" />}</div>;
};

export default Authenticate;
