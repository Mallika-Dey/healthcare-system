import React, { useEffect, useRef } from "react";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

const MyMap = () => {
  const mapRef = useRef(null);

  useEffect(() => {
    if (!mapRef.current) {
      mapRef.current = L.map("map").setView(
        [23.795121821998126, 90.42390733684717],
        13
      );

      L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(mapRef.current);

      L.marker([23.795121821998126, 90.42390733684717])
        .addTo(mapRef.current)
        .bindPopup("Bashtola")
        .openPopup();
    }
  }, []);

  return (
    <>
      <div id="map" style={{ height: "400px", zIndex: 1 }}></div>
    </>
  );
};

export default MyMap;
