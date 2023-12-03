import MyMap from "../components/map.component.jsx";
import CarouselComponent from "./casrousel.jsx";
import {
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBCard,
  MDBCardImage,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
} from "mdb-react-ui-kit";

const Home = () => {
  return (
    <div className="mb-3">
      <CarouselComponent />
      <MDBContainer className="mb-5 mt-5">
        <MDBRow style={{ marginTop: "20px" }}>
          <h4 className="text-center">Our Services</h4>
          <MDBCol md="4">
            <MDBCard>
              <MDBCardImage
                src="https://www.aristamd.com/wp-content/uploads/PCP-Consultation-with-Specialists-Blog.jpg"
                alt="Consultation"
                position="top"
              />
              <MDBCardBody>
                <MDBCardTitle>Consultation Services</MDBCardTitle>
                <MDBCardText>
                  Discover how you can connect with health experts for
                  personalized advice and consultations.
                </MDBCardText>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>

          <MDBCol md="4">
            <MDBCard>
              <MDBCardImage
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYnArdjAlGELFYGMCDK_uoHruchq4gsCqNdg&usqp=CAU"
                alt="Health Progress"
                position="top"
              />
              <MDBCardBody>
                <MDBCardTitle>Health Progress Track</MDBCardTitle>
                <MDBCardText>
                  Learn about tracking your health progress and getting custom
                  recommendations based on your goals.
                </MDBCardText>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>

          <MDBCol md="4">
            <MDBCard>
              <MDBCardImage
                src="https://mcr.health/wp-content/uploads/2021/03/If-You-Cant-Get-a-Doctors-Appointment.jpg"
                alt="Doctor Appointment"
                position="top"
              />
              <MDBCardBody>
                <MDBCardTitle>Doctor Appointment Services</MDBCardTitle>
                <MDBCardText>
                  Explore options to find and schedule an appointment with
                  qualified doctors for your health needs.
                </MDBCardText>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
      <MDBCard>
        {" "}
        <MDBRow>
          <MDBCol md="4" className="mx-4 mt-5">
            <h3>About Us</h3>
            <p>
              {" "}
              we are dedicated to providing compassionate, high-quality
              healthcare services that improve the wellbeing of our community.
              Established in [Year], our healthcare system has been at the
              forefront of medical excellence, integrating advanced technology
              with a human touch.
            </p>
          </MDBCol>
          <MDBCol md="7">
            <h3 className="text-center fw-bold">Location</h3>
            <MyMap />
          </MDBCol>
        </MDBRow>
      </MDBCard>

      {/* <MyMap /> */}
    </div>
  );
};

export default Home;
