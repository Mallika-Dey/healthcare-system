package com.example.cdssservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class ProgressHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private double bmi;
    private double bmr;
    private double sugarLevel;
    private LocalDate date;

    private ProgressHistory(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.bmi = builder.bmi;
        this.bmr = builder.bmr;
        this.sugarLevel = builder.sugarLevel;
        this.date = builder.date;
    }

    public Integer getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public double getBmi() {
        return bmi;
    }

    public double getBmr() {
        return bmr;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public LocalDate getDate() {
        return date;
    }

    public static class Builder {
        private Integer id;
        private Long userId;
        private double bmi;
        private double bmr;
        private double sugarLevel;
        private LocalDate date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder bmi(double bmi) {
            this.bmi = bmi;
            return this;
        }

        public Builder bmr(double bmr) {
            this.bmr = bmr;
            return this;
        }

        public Builder sugarLevel(double sugarLevel) {
            this.sugarLevel = sugarLevel;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ProgressHistory build() {
            return new ProgressHistory(this);
        }
    }
}
