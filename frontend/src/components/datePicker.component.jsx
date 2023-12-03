import PropTypes from "prop-types";
import generatePicker from "antd/lib/date-picker/generatePicker";
import dayjs from "dayjs";
import dayjsGenerateConfig from "rc-picker/lib/generate/dayjs";

const DatePicker = generatePicker(dayjsGenerateConfig);

export default function DatePickerField({
  label,
  labelClass = "text-muted",
  required,
  onChange,
  error,
  value,
  labelClassName,
  inline = false,
  className,
  ...rest
}) {
  const _onChange = (date, dateString) => {
    if (onChange) {
      onChange(dateString);
    }
  };

  return (
    <div>
      <label className={labelClassName}>{label}</label>
      <div>
        <DatePicker
          className="form-control"
          onChange={_onChange}
          value={value ? dayjs(value) : undefined}
          {...rest}
        />
      </div>
      {error?.type && (
        <div className="text-danger small mt-1">{error?.message}</div>
      )}
    </div>
  );
}

DatePickerField.propTypes = {
  label: PropTypes.string,
  labelClass: PropTypes.string,
  required: PropTypes.bool,
  onChange: PropTypes.func,
  error: PropTypes.object,
  value: PropTypes.any,
  inline: PropTypes.bool,
  className: PropTypes.string,
};

DatePickerField.defaultProps = {
  labelClass: "text-muted",
  required: false,
  inline: false,
};
