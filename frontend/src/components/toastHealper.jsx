import { message } from "antd";

export default class ToastHelper {
  static genericErrorMsg = "Something went wrong, Try again!";
  static genericSuccessMsg = "Success!";

  static error(text) {
    message.error({
      content: text || ToastHelper.genericErrorMsg,
      className: "flex justify-end",
      style: {
        marginTop: "5px",
        zIndex: 9999,
      },
    });
  }

  static success(text) {
    message.success({
      content: text || ToastHelper.genericSuccessMsg,
      className: "flex justify-end",
      style: {
        marginTop: "5px",
        zIndex: 9999,
      },
    });
  }
}
