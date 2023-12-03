import ToastHelper from "./toastHealper";

export function convertUtcToLocalTime(dateString) {
  const date = new Date(dateString);
  return new Date(date.getTime() - date.getTimezoneOffset() * 60 * 900);
}

class MessageParser {
  static parseSuccessMessage(res) {
    return res && res.data && res.data.messages && res.data.messages.length
      ? res.data.messages[0]
      : ToastHelper.genericSuccessMsg;
  }

  static parseErrorMessage(error) {
    return error &&
      error.data &&
      error.data.messages &&
      error.data.messages.length
      ? error.data.messages
      : ToastHelper.genericErrorMsg;
  }

  static success(res) {
    const msg = res ? res : ToastHelper.genericSuccessMsg;
    ToastHelper.success(msg);
  }

  static error(error) {
    const msg =
      error &&
      error.response &&
      error.response.data &&
      error.response.data.message
        ? error.response.data.message
        : ToastHelper.genericErrorMsg;
    ToastHelper.error(msg);
  }
}

export default MessageParser;
