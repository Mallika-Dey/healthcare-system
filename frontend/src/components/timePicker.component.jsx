import PropTypes from "prop-types";
import dayjs from "dayjs";
import { TimePicker } from "antd";

const format = "HH:mm";

export default function TimePickerField({
  label,
  labelClass = "text-muted",
  required,
  onChange,
  error,
  value,
  inline = false,
  className,
  ...rest
}) {
  const _onChange = (time, timeString) => {
    if (onChange) {
      onChange(timeString);
    }
  };

  return (
    <div>
      <div>
        {label && <label>{label}</label>}
        <TimePicker
          format={format}
          className="form-control"
          wrapperClass="mb-4"
          onChange={(time, timeString) => {
            if (onChange) {
              onChange(timeString);
            }
          }}
          value={value ? dayjs(value, format) : undefined}
          {...rest}
        />
      </div>
      {error?.type && (
        <div className="text-danger small mt-1">{error?.message}</div>
      )}
    </div>
  );
}

TimePickerField.propTypes = {
  label: PropTypes.string,
  labelClass: PropTypes.string,
  required: PropTypes.bool,
  onChange: PropTypes.func,
  error: PropTypes.object,
  value: PropTypes.any,
  inline: PropTypes.bool,
  className: PropTypes.string,
};

TimePickerField.defaultProps = {
  labelClass: "text-muted",
  required: false,
  inline: false,
};
