import React from "react";

const ButtonComponent = ({
  buttonStyle,
  onClick,
  label,
  width,
  height,
  customStyle,
}) => {
  const buttonStyles = {
    width: width || "auto",
    height: height || "auto",
    ...customStyle,
  };

  return (
    <button
      className={`btn ${buttonStyle}`}
      style={buttonStyles}
      onClick={onClick}
    >
      {label}
    </button>
  );
};

export default ButtonComponent;
