package com.example.demo.chem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.demo.operation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Document("chem2")
public class ChemModel {
  @Id 
  @Getter @Setter
  private String id;

  @Getter @Setter
  @NotEmpty(message = "The name is required")
  private String name;
  
  @Setter
  private boolean state;

  @Getter @Setter
  private List<Operation> ops;

  @Getter @Setter
  @NotEmpty(message = "The current location is required")
  private String currentLocation;

  @Getter @Setter
  private boolean open;

  @Getter @Setter
  @Size(
    min = 0,
    message = "The amount can't be less than 0"
  )
  private Integer amount;

  // public ChemModel() {}

  // public ChemModel(
  //     String name,
  //     String currentLocation,
  //     Integer amount
  //   ) {
  //     this.name = name;
  //     this.state = true;
  //     this.ops = new ArrayList<>();
  //     this.currentLocation = currentLocation;
  //     this.open = false;
  //     this.amount = amount;
  // }

  public String toString() {
    return "Name: " + name + "\n" +
            "State: " + (state? "Active": "No active") + "\n" +
            "Current location: " + currentLocation + "\n" +
            "Open: " + (open? "yes": "no") + "\n" +
            "Amount:" + amount;
  }

  public boolean getState() {
    return state;
  }

  public boolean getOpen() {
    return open;
  }

}
