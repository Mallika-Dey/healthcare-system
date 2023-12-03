import PropTypes from "prop-types";

const InputField = ({
  name,
  label,
  type,
  rules,
  placeholder,
  register,
  errors,
  className,
  labelClassName,
  step,
}) => {
  return (
    <div className="form-group">
      <p className={labelClassName}>{label}</p>
      <input
        id={name}
        {...register(name, rules)}
        type={type}
        placeholder={placeholder}
        className={className}
        step={step ? step : "1"}
      />
      {errors[name] && (
        <span role="alert">
          {errors[name].type === "required"
            ? "This is required"
            : errors[name].type === "maxLength"
            ? "Max length exceeded"
            : "Validation error"}
        </span>
      )}
    </div>
  );
};

InputField.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string,
  type: PropTypes.string.isRequired,
  rules: PropTypes.object.isRequired,
  placeholder: PropTypes.string.isRequired,
  register: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

export default InputField;
