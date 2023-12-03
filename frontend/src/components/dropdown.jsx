import React from "react";
import Select from "react-select";
import PropTypes from "prop-types";

const DropdownField = ({
  name,
  style,
  label,
  field,
  options,
  rules,
  register,
  errors,
  labelClassName,
  disabled = false,
}) => {
  const handleChange = (selectedOption) => {
    field.onChange(selectedOption ? selectedOption.value : null);
  };

  const customStyles = {
    control: (provided) => ({
      ...provided,
      ...style,
    }),
  };

  return (
    <div>
      <label className={labelClassName}>{label}</label>
      <Select
        {...field}
        id={name}
        options={options}
        {...register(name, rules)}
        onChange={handleChange}
        value={options.find((option) => option.value === field.value)}
        styles={customStyles}
        isDisabled={disabled}
      />
      {errors[name] && (
        <span role="alert" className="text-danger small mt-1">
          {"This is required"}
        </span>
      )}
    </div>
  );
};

DropdownField.propTypes = {
  label: PropTypes.string.isRequired,
  field: PropTypes.object.isRequired,
  options: PropTypes.array.isRequired,
  rules: PropTypes.object.isRequired,
  register: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

export default DropdownField;
