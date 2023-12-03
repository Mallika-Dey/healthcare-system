import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Notification from "../notification";

function Navigation() {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    navigate("/login");
  };

  return (
    <header>
      <nav
        className="navbar navbar-expand-lg navbar-light bg-light fixed-top"
        style={{ zIndex: 99 }}
      >
        <div className="container-fluid">
          <button
            className="navbar-toggler"
            type="button"
            data-mdb-toggle="collapse"
            data-mdb-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <i className="fas fa-bars"></i>
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <a className="navbar-brand mt-2 mx-3 mt-lg-0" href="#">
              <img
                src="https://png.pngtree.com/png-clipart/20200722/original/pngtree-medical-logo-design-health-care-logo-pharmacy-healthcare-vecto-png-image_5059502.jpg"
                className="rounded-circle"
                height="45"
                width="45"
                alt="Logo"
                loading="lazy"
              />
            </a>

            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item active">
                <Link to="/" className="nav-link">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link to="/all-doctor" className="nav-link">
                  All Doctors
                </Link>
              </li>
              <li className="nav-item active">
                <Link to="/all-dept" className="nav-link">
                  Departments
                </Link>
              </li>

              {token && (
                <>
                  {role != "ADMIN" && (
                    <li className="nav-item">
                      <Link to="/consult" className="nav-link">
                        Consult
                      </Link>
                    </li>
                  )}

                  {role === "DOCTOR" && (
                    <>
                      <li className="nav-item">
                        <Link to="/doctor-profile" className="nav-link">
                          Profile
                        </Link>
                      </li>
                      <li className="nav-item">
                        <Link to="/doctor-dashboard" className="nav-link">
                          Dashboard
                        </Link>
                      </li>
                    </>
                  )}

                  {role === "PATIENT" && (
                    <>
                      <li className="nav-item">
                        <Link to="/patient-dashboard" className="nav-link">
                          Dashboard
                        </Link>
                      </li>
                      <li className="nav-item">
                        <Link to="/patient-profile" className="nav-link">
                          Profile
                        </Link>
                      </li>
                    </>
                  )}

                  {role === "ADMIN" && (
                    <li className="nav-item">
                      <Link to="/admin-dashboard" className="nav-link">
                        Dashboard
                      </Link>
                    </li>
                  )}
                </>
              )}

              {!token && (
                <>
                  <li className="nav-item">
                    <a href="/register" className="nav-link">
                      Register
                    </a>
                  </li>
                  <li className="nav-item">
                    <a href="/login" className="nav-link">
                      Login
                    </a>
                  </li>
                </>
              )}
            </ul>
          </div>

          {token && (
            <div className="d-flex align-items-center mx-5">
              <div className="dropdown text-reset me-3 mx-2 mt-1 dropdown-toggle hidden-arrow">
                <Notification />
              </div>

              <button
                onClick={handleLogout}
                className="nav-link btn btn-light"
                style={{
                  border: "none",
                  outline: "none",
                  textTransform: "none", // Prevent text from becoming uppercase
                  backgroundColor: "transparent", // Set the initial background color
                  transition: "background-color 0.3s", // Add a transition for background color change
                }}
              >
                Logout
              </button>
            </div>
          )}
        </div>
      </nav>
    </header>
  );
}

export default Navigation;
