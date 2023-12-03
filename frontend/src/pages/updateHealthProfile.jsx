import React, { useState, useEffect } from "react";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBRow,
  MDBCol,
} from "mdb-react-ui-kit";
import { useForm, Controller } from "react-hook-form";
import InputField from "../components/InputField";
import DropdownField from "../components/dropdown";
import Button from "../components/buttons/button";
import MessageParser from "../components/messageParser";
import * as PatientService from "../service/patientService";

const UpdateHealthProfile = () => {
  const [iniHealth, setIniHealth] = useState(null);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    setValue,
  } = useForm();

  const fetchProfile = () => {
    PatientService.getHealth()
      .then((response) => {
        setIniHealth(response);
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  const onSubmit = async (data) => {
    PatientService.updateHealth(data)
      .then((response) => {
        console.log(response);
        MessageParser.success(response.message);
      })
      .catch((error) => {
        MessageParser.error(error);
      });
  };

  useEffect(() => {
    if (iniHealth) {
      setValue("age", iniHealth.age);
      setValue("weight", iniHealth.weight);
      setValue("height", iniHealth.height);
      setValue("bloodGroup", iniHealth.bloodGroup);
      setValue("goalType", iniHealth.goalType);
      setValue("activityLevel", iniHealth.activityLevel);
      setValue("gender", iniHealth.gender);
      setValue("bloodPressure", iniHealth.bloodPressure);
      setValue("smoke", iniHealth.smoke);
      setValue("sinusitis", iniHealth.sinusitis);
      setValue("previousStroke", iniHealth.previousStroke);
      setValue(
        "familyHistoryCardiovascularDisease",
        iniHealth.familyHistoryCardiovascularDisease
      );
      setValue("highCholesterol", iniHealth.highCholesterol);
      setValue("chestPain", iniHealth.chestPain);
      setValue("sugarLevel", iniHealth?.sugarLevel);
      setValue("fastingBloodGlucoseLevel", iniHealth?.fastingBloodGlucoseLevel);
      setValue("alcoholConsumption", iniHealth?.alcoholConsumption);
      setValue("thirstLevel", iniHealth?.thirstLevel);
    }
  }, [iniHealth, setValue]);

  return (
    <div style={{ marginTop: "6em" }}>
      <MDBContainer fluid>
        <MDBRow className="justify-content-center align-items-center m-5">
          <MDBCard style={{ backgroundColor: "#faf9e3" }}>
            <MDBCardBody className="px-4">
              <h3
                className="fw-bold mb-4 pb-2 pb-md-0 mb-md-5"
                style={{ marginLeft: "13em" }}
              >
                Health Information Form
              </h3>

              <form
                onSubmit={handleSubmit(onSubmit)}
                className="form space-y-4 md:space-y-6"
              >
                <MDBRow className="mb-3">
                  <MDBCol md="6">
                    <div className="form-group">
                      <InputField
                        name="age"
                        label="Age"
                        type="number"
                        className="form-control"
                        labelClassName="inputfield-margin"
                        rules={{
                          required: true,
                          min: 1,
                          max: 150,
                          positive: true,
                        }}
                        placeholder="Age"
                        register={register}
                        errors={errors}
                        id="age"
                      />
                    </div>
                  </MDBCol>
                  <MDBCol>
                    <div className="form-group">
                      <InputField
                        name="weight"
                        label="Weight"
                        type="number"
                        className="form-control"
                        labelClassName="inputfield-margin"
                        rules={{
                          required: true,
                          min: 1,
                          max: 150,
                          positive: true,
                        }}
                        placeholder="Weight"
                        register={register}
                        errors={errors}
                        id="weight"
                        step="0.1"
                      />
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol md="6">
                    <div className="form-group">
                      <InputField
                        name="height"
                        label="Height"
                        type="number"
                        className="form-control"
                        labelClassName="inputfield-margin"
                        rules={{
                          required: true,
                          min: 1,
                          max: 150,
                          positive: true,
                        }}
                        placeholder="Height"
                        register={register}
                        errors={errors}
                        id="height"
                        step="0.001"
                      />
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="gender"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="gender"
                            label="Gender"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              { label: "Male", value: "MALE" },
                              { label: "Female", value: "FEMALE" },
                            ]}
                            register={register}
                            errors={errors}
                            disabled={true}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol md="6">
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="bloodPressure"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="bloodPressure"
                            label="Blood Pressure:"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              { label: "High", value: "HIGH" },
                              { label: "Low", value: "LOW" },
                              { label: "Normal", value: "NORMAL" },
                            ]}
                            register={register}
                            errors={errors}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>

                  <MDBCol md="6">
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="activityLevel"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="activityLevel"
                            label="Activity Level"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              { label: "Sedentary", value: "SEDENTARY" },
                              {
                                label: "Lightly Active",
                                value: "LIGHTLY_ACTIVE",
                              },
                              {
                                label: "Moderately Active",
                                value: "MODERATELY_ACTIVE",
                              },
                              { label: "Very Active", value: "VERY_ACTIVE" },
                            ]}
                            register={register}
                            errors={errors}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol>
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="goalType"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="goalType"
                            label="Goal Type"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              { label: "Lose Weight", value: "LOSE_WEIGHT" },
                              {
                                label: "Build Muscle",
                                value: "BUILD_MUSCLE",
                              },
                              {
                                label: "Improve Fitness",
                                value: "IMPROVE_FITNESS",
                              },
                              {
                                label: "Reduce Stress",
                                value: "REDUCE_STRESS",
                              },
                            ]}
                            register={register}
                            errors={errors}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group">
                      <InputField
                        name="sugarLevel"
                        label="Sugar Level"
                        type="number"
                        className="form-control"
                        labelClassName="inputfield-margin"
                        rules={{ required: true, min: 0, positive: true }}
                        placeholder="Sugar Level"
                        register={register}
                        errors={errors}
                        id="sugarLevel"
                        step="0.1"
                      />
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol md="6">
                    <div className="form-group">
                      <InputField
                        name="fastingBloodGlucoseLevel"
                        label="Fasting Blood Glucose Level"
                        type="number"
                        className="form-control"
                        labelClassName="inputfield-margin"
                        rules={{ required: false, min: 0, positive: true }}
                        placeholder="Fasting Blood Glucose Level"
                        register={register}
                        errors={errors}
                        id="fastingBloodGlucoseLevel"
                        step="0.1"
                      />
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="alcoholConsumption"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="alcoholConsumption"
                            label="Alcohol Consumption"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              { label: "None", value: "NONE" },
                              { label: "Occasional", value: "OCCASIONAL" },
                              { label: "Moderate", value: "MODERATE" },
                              { label: "Heavy", value: "HEAVY" },
                            ]}
                            register={register}
                            errors={errors}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol md="6">
                    <div className="form-group custom-label-wrapper">
                      <Controller
                        name="thirstLevel"
                        control={control}
                        render={({ field }) => (
                          <DropdownField
                            name="thirstLevel"
                            label="Thirst Level"
                            field={field}
                            rules={{ required: true }}
                            options={[
                              {
                                label: "More than usual",
                                value: "MORE_THAN_USUAL",
                              },
                              { label: "Normal", value: "NORMAL" },
                            ]}
                            register={register}
                            errors={errors}
                          />
                        )}
                      />
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div
                      className="form-group custom-label-wrapper"
                      style={{ marginTop: "2em" }}
                    >
                      <input
                        type="checkbox"
                        {...register("familyHistoryCardiovascularDisease")}
                      />{" "}
                      Family History Of Cardiovascular Disease
                    </div>
                  </MDBCol>
                </MDBRow>

                <MDBRow className="mb-3">
                  <MDBCol>
                    <div className="form-group">
                      <input type="checkbox" {...register("smoke")} /> Smoke
                    </div>
                  </MDBCol>
                  <MDBCol>
                    <div className="form-group">
                      <input
                        type="checkbox"
                        {...register("sinusitis")}
                        name="sinusitis"
                      />{" "}
                      Sinusitis
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group">
                      <input
                        type="checkbox"
                        {...register("previousStroke")}
                        name="previousStroke"
                      />{" "}
                      Previous Stroke
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group">
                      <input
                        type="checkbox"
                        {...register("highCholesterol")}
                        name="highCholesterol"
                      />{" "}
                      High Cholesterol
                    </div>
                  </MDBCol>

                  <MDBCol>
                    <div className="form-group">
                      <input type="checkbox" {...register("chestPain")} /> Chest
                      Pain
                    </div>
                  </MDBCol>
                </MDBRow>

                <Button buttonStyle="btn btn-primary" name="update" />
              </form>
            </MDBCardBody>
          </MDBCard>
        </MDBRow>
      </MDBContainer>
    </div>
  );
};

export default UpdateHealthProfile;
