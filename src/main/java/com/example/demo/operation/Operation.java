package com.example.demo.operation;

import java.time.LocalDateTime;

public class Operation {
  private String type;
  private String place;
  private Integer amount;
  private LocalDateTime dateTime;

  public Operation(
      String type,
      String place,
      Integer amount,
      LocalDateTime dateTime
  ) {
    this.type = type;
    this.place = place;
    this.amount = amount;
    this.dateTime = dateTime;
  }

  public String getType() {
    return type;
  }

  public String getPlace() {
    return place;
  }

  public Integer getAmount() {
    return amount;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }


  public void setType(String type) {
    this.type = type;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String toString() {
    return "Type: " + type + "\n" +
            "Place: " + place + "\n" +
            "Amount: " + amount + "\n" +
            "DateTime: " + dateTime.toString();
  }
}
