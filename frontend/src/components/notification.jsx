import React, { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";

const Notification = () => {
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [isOpen, setIsOpen] = useState(false);
  const userId = localStorage.getItem("userId");

  const handleBellClick = () => {
    setIsOpen(!isOpen);
    console.log("hell");
    if (isOpen) {
      setNotifications(notifications.map((n) => ({ ...n, isNew: false })));
    }
    setUnreadCount(0);
  };

  useEffect(() => {
    const client = new Client({
      //brokerURL: "ws://localhost:8088/healthcare-websocket",
      brokerURL:
        "ws://localhost:9090/notification-service/healthcare-websocket",
      reconnectDelay: 1000,
    });

    client.onConnect = () => {
      console.log("Connected to WebSocket");
      client.subscribe(`/user/${userId}/notification`, (onMessageReceived) => {
        console.log(onMessageReceived);
        const messageContent = JSON.parse(onMessageReceived.body);
        setNotifications((prevNotifications) => [
          { message: messageContent, isNew: true },
          ...prevNotifications,
        ]);
        console.log(notifications);
        setUnreadCount(unreadCount + 1);
      });
    };

    client.activate();

    return () => {
      client.deactivate();
    };
  }, [notifications, unreadCount]);

  return (
    <>
      <i className="fas fa-bell" onClick={handleBellClick}></i>
      <span className="badge rounded-pill badge-notification bg-danger">
        {unreadCount > 0 && <span className="unread-count">{unreadCount}</span>}
      </span>
      {isOpen && (
        <div className="notification-list">
          {notifications &&
            notifications.map((notification, index) => (
              <div
                key={index}
                className={`notification-item ${
                  notification.isNew ? "new-notification" : ""
                }`}
              >
                {notification.message.message}
              </div>
            ))}
        </div>
      )}
    </>
  );
};

export default Notification;
