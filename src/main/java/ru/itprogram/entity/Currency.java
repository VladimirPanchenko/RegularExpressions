package ru.itprogram.entity;

import java.util.Objects;

public class Currency {
    private int digitalCode;
    private String letterCode;
    private int oneUnit;
    private String currency;
    private double course;

    public Currency() {

    }

    public Currency(int digitalCode, String letterCode,
                    int oneUnit, String currency, double course) {
        this.digitalCode = digitalCode;
        this.letterCode = letterCode;
        this.oneUnit = oneUnit;
        this.currency = currency;
        this.course = course;
    }

    public int getDigitalCode() {
        return digitalCode;
    }

    public void setDigitalCode(int digitalCode) {
        this.digitalCode = digitalCode;
    }

    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public int getOneUnit() {
        return oneUnit;
    }

    public void setOneUnit(int oneUnit) {
        this.oneUnit = oneUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency1 = (Currency) o;
        return digitalCode == currency1.digitalCode &&
                oneUnit == currency1.oneUnit &&
                Double.compare(currency1.course, course) == 0 &&
                Objects.equals(letterCode, currency1.letterCode) &&
                Objects.equals(currency, currency1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitalCode, letterCode, oneUnit, currency, course);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "digitalCode=" + digitalCode +
                ", letterCode='" + letterCode + '\'' +
                ", oneUnit=" + oneUnit +
                ", currency='" + currency + '\'' +
                ", course=" + course +
                '}';
    }
}
