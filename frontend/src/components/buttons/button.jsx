import React from "react";

const Button = ({ buttonStyle, name, width, height }) => {
  const buttonStyles = {
    width: width || "auto",
    height: height || "auto",
    display: "block",
    margin: "0 auto",
  };
  return (
    <button className={buttonStyle} style={buttonStyles}>
      {name}
    </button>
  );
};

export default Button;
