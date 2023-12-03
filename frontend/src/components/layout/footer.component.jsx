function Footer() {
  return (
    <footer className="text-center text-lg-start bg-light text-muted">
      <section className="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
        <div className="me-5 d-none d-lg-block">
          <span>Connect with us for health updates:</span>
        </div>
        <div>
          <a href="#" className="me-4 text-reset">
            <i className="fab fa-facebook-f"></i>
          </a>
          <a href="#" className="me-4 text-reset">
            <i className="fab fa-twitter"></i>
          </a>
          <a href="#" className="me-4 text-reset">
            <i className="fab fa-linkedin"></i>
          </a>
        </div>
      </section>
      <section>
        <div className="container text-center text-md-start mt-5">
          <div className="row mt-3">
            <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mb-4">
              <h6 className="text-uppercase fw-bold mb-4">
                <i className="fas fa-clinic-medical me-3"></i>Our Services
              </h6>
              <p>
                Offering a comprehensive range of medical services to cater to
                your health needs.
              </p>
            </div>
            <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mb-4">
              <h6 className="text-uppercase fw-bold mb-4">
                Healthcare Resources
              </h6>
              <p>
                <a href="#!" className="text-reset">
                  Patient Care
                </a>
              </p>
              <p>
                <a href="#!" className="text-reset">
                  Health Tips
                </a>
              </p>
              <p>
                <a href="#!" className="text-reset">
                  Wellness Programs
                </a>
              </p>
              <p>
                <a href="#!" className="text-reset">
                  FAQs
                </a>
              </p>
            </div>
            <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mb-4">
              <h6 className="text-uppercase fw-bold mb-4">Useful Links</h6>
              <p>
                <a href="#!" className="text-reset">
                  Find a Doctor
                </a>
              </p>
              <p>
                <a href="#!" className="text-reset">
                  Book an Appointment
                </a>
              </p>
              <p>
                <a href="#!" className="text-reset">
                  Contact Support
                </a>
              </p>
            </div>
            <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
              <h6 className="text-uppercase fw-bold mb-4">Contact Us</h6>
              <p>
                <i className="fas fa-hospital-symbol me-3"></i> 123 Health St,
                City, Country
              </p>
              <p>
                <i className="fas fa-envelope me-3"></i>
                support@healthsystem.com
              </p>
              <p>
                <i className="fas fa-phone me-3"></i> +1 (234) 567-8901
              </p>
            </div>
          </div>
        </div>
      </section>
      <div
        className="text-center p-4"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.05)" }}
      >
        © 2023 Healthcare System:
        <a className="text-reset fw-bold" href="https://healthcaresystem.com/">
          HealthCareSystem.com
        </a>
      </div>
    </footer>
  );
}

export default Footer;
