import React from "react";
import {
  MDBCarousel,
  MDBCarouselItem,
  MDBCarouselCaption,
} from "mdb-react-ui-kit";

export default function CarouselComponent() {
  return (
    <MDBCarousel showIndicators showControls fade>
      <MDBCarouselItem itemId={1}>
        <img
          src="./slide-1-2000.jpg"
          className="d-block w-100"
          style={{ height: "37.2em" }}
          alt="..."
        />
        <MDBCarouselCaption>
          <h5>Doctor Appointment & Telemedicine Service</h5>
          <p>
            Seamless Healthcare at Your Fingertips: Doctor Appointments and
            Telemedicine Services
          </p>
        </MDBCarouselCaption>
      </MDBCarouselItem>

      <MDBCarouselItem itemId={2}>
        <img
          src="./slide-2-2000.jpg"
          className="d-block w-100"
          style={{ height: "37.2em" }}
          alt="..."
        />
        <MDBCarouselCaption>
          <h5>Personalized Health Recommendations</h5>
          <p>Your Wellness, Our Priority</p>
        </MDBCarouselCaption>
      </MDBCarouselItem>

      <MDBCarouselItem itemId={3}>
        <img
          src="./slide-3-2000.jpg"
          className="d-block w-100"
          style={{ height: "37.2em" }}
          alt="..."
        />
        <MDBCarouselCaption>
          <h5>Our Services</h5>
          <p>Advanced Facilities for Exceptional Care</p>
        </MDBCarouselCaption>
      </MDBCarouselItem>
    </MDBCarousel>
  );
}
