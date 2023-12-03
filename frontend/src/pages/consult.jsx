import React, { useState, useEffect } from "react";
import { Button, Card, Radio, Progress, Space } from "antd";
import * as ConsultService from "../service/cdssService";
import MessageParser from "../components/messageParser";
import { ConsultResquest } from "../models/consult";

const Consult = () => {
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [options, setOptions] = useState([]);
  const [running, setRunning] = useState(true);
  const [progress, setProgress] = useState(0);
  const [selectedValue, setSelectedValue] = useState(undefined);

  const startConsultation = async () => {
    ConsultService.startConsult()
      .then((response) => {
        setCurrentQuestion(response.message);
        setOptions(response.options);
        setProgress(0);
        setRunning(!response.conversationEnded);
      })
      .catch((error) => MessageParser.error(error));
  };

  useEffect(() => {
    startConsultation();
  }, []);

  const handleAnswer = (value) => {
    ConsultService.continueConsult(new ConsultResquest({ answer: value }))
      .then((response) => {
        setCurrentQuestion(response.message);
        setOptions(response.options);
        setRunning(!response.conversationEnded);
        if (response.conversationEnded) {
          setProgress(100);
        } else {
          setProgress(progress + 5);
          setSelectedValue(undefined);
        }
      })
      .catch((error) => MessageParser.error(error));
  };

  return (
    <>
      <div className="container" style={{ marginTop: "100px" }}>
        <header className="consult-header text-center mb-5">
          <h1>Consultation Process</h1>
          <h3>Welcome to our interactive consultation!</h3>
          <marquee style={{ fontSize: "25px", fontWeight: "bold" }}>
            Get your health progress & health recommendations
          </marquee>
        </header>
        <div className="row justify-content-center">
          <div className="col-md-8">
            <Card
              className="mb-5"
              title={<div className="card-title">{currentQuestion}</div>}
              style={{ backgroundColor: "#baf7ca" }}
            >
              <Progress
                percent={progress}
                status={running ? "active" : "normal"}
              />
              {running ? (
                <Radio.Group
                  onChange={(e) => handleAnswer(e.target.value)}
                  name="answer"
                  className="radio-button-group p-3"
                  value={selectedValue}
                >
                  <Space direction="vertical">
                    {options.map((option, index) => (
                      <Radio
                        key={index}
                        value={option}
                        className="d-block my-2"
                      >
                        {" "}
                        {option}
                      </Radio>
                    ))}
                  </Space>
                </Radio.Group>
              ) : (
                <Button onClick={startConsultation} className="btn-primary">
                  {" "}
                  Start Again
                </Button>
              )}
            </Card>
          </div>
        </div>
      </div>
    </>
  );
};

export default Consult;
