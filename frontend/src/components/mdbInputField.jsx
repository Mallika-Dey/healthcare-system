import PropTypes from "prop-types";
import { MDBInput } from "mdb-react-ui-kit";
import "../styles/login.css";

const MDBInputField = ({
  name,
  label,
  type,
  rules,
  placeholder,
  register,
  errors,
}) => {
  return (
    <>
      <MDBInput
        id={name}
        {...register(name, rules)}
        label={label}
        type={type}
        placeholder={placeholder}
        wrapperClass="mb-3"
        size="lg"
      />
      {errors[name] && (
        <span role="alert" className="text-danger small">
          {errors[name].type === "required"
            ? "This is required"
            : errors[name].type === "maxLength"
            ? "Max length exceeded"
            : "Validation error"}
        </span>
      )}
    </>
  );
};

MDBInputField.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string,
  type: PropTypes.string.isRequired,
  rules: PropTypes.object.isRequired,
  placeholder: PropTypes.string,
  register: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

export default MDBInputField;
