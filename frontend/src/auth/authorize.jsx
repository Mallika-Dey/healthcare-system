import { Navigate, Outlet } from "react-router-dom";

const Authorize = ({ approvedRole }) => {
  const role = localStorage.getItem("role");
  console.log(approvedRole);

  return <div>{role === approvedRole ? <Outlet /> : <Navigate to="/" />}</div>;
};

export default Authorize;
