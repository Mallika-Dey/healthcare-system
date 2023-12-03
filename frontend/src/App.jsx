import Layout from "./components/layout/layout.component";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./pages/login";
import Registration from "./pages/patientRegistration";
import AdminRegistration from "./pages/adminRegistration";
import DoctorRegistration from "./pages/doctorRegistration";
import AllDepartments from "./pages/department";
import DepartmentDoctors from "./pages/departmentInfo";
import DoctorDetailsPage from "./pages/doctorDetails";
import Appointment from "./pages/appointment";
import AllDoctors from "./pages/allDoctors";
import BloodBank from "./pages/bloodBank";
import VideoConference from "./pages/videoCall";
import AdminDashboard from "./pages/adminDashboard";
import Consult from "./pages/consult";
import Authenticate from "./auth/authenticate";
import UnAuthenticate from "./auth/unAuthencate";
import Authorize from "./auth/authorize";
import DoctorDashboard from "./pages/doctorDashboard.jsx";
import DoctorProfile from "./pages/doctorProfile.jsx";
import PatientProfile from "./pages/patientProfile.jsx";
import HealthProfile from "./pages/createHealthProfile.jsx";
import UpdateHealthProfile from "./pages/updateHealthProfile.jsx";
import PatientDashboard from "./pages/patientDashboard.jsx";
import Home from "./pages/home.jsx";
import RegisterSelect from "./pages/registerSelection.jsx";

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/all-doctor" element={<AllDoctors />} />
          <Route path="/consult" element={<Consult />} />
          <Route path="/all-dept" element={<AllDepartments />} />
          <Route
            path="/deptinfo/:id/:deptName"
            element={<DepartmentDoctors />}
          />
          <Route path="/search-people" element={<BloodBank />} />

          <Route path="/doctorinfo/:doctorId" element={<DoctorDetailsPage />} />

          <Route element={<Authenticate />}>
            <Route path="/call" element={<VideoConference />} />

            <Route element={<Authorize approvedRole="PATIENT" />}>
              <Route path="/appointment/:doctorId" element={<Appointment />} />
              <Route path="/patient-profile" element={<PatientProfile />} />
              <Route path="/add-health-data" element={<HealthProfile />} />
              <Route
                path="/update-health-data"
                element={<UpdateHealthProfile />}
              />
              <Route path="/patient-dashboard" element={<PatientDashboard />} />
              <Route path="/consult" element={<Consult />} />
            </Route>

            <Route element={<Authorize approvedRole="ADMIN" />}>
              <Route path="/admin-dashboard" element={<AdminDashboard />} />
            </Route>

            <Route element={<Authorize approvedRole="DOCTOR" />}>
              <Route path="/doctor-profile" element={<DoctorProfile />} />
              <Route path="/doctor-dashboard" element={<DoctorDashboard />} />
            </Route>
          </Route>

          <Route element={<UnAuthenticate />}>
            <Route path="/register" element={<RegisterSelect />} />
            <Route path="/admin-register" element={<AdminRegistration />} />
            <Route path="/doctor-register" element={<DoctorRegistration />} />
            <Route path="/patient-register" element={<Registration />} />
            <Route path="/login" element={<Login />} />
          </Route>
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
