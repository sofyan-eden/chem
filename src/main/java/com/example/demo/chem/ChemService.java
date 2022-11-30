package com.example.demo.chem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.operation.Operation;

@Service
public class ChemService {
  private final ChemRepository chemRepository;

  @Autowired
  public ChemService(ChemRepository chemRepository) {
    this.chemRepository = chemRepository;
  }

  public List<ChemModel> getAll() {
    return chemRepository.findAll();
  }

  public ChemModel create(ChemModel chem) {
    return chemRepository.save(chem);
  }

  public ResponseEntity<Object> setState(boolean state, String id) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (ch.isPresent()) {
      if (ch.get().getState() == state) {
        if (state)
          return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(Map.of("message", "The chem is already active"));

        else
          return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(Map.of("message", "The chem is already unactive"));
      }
      ch.get().setState(state);
      chemRepository.save(ch.get());
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(Map.of("message", "The chem status changed successfully"));
    }
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", "This chem doesn't exist"));
  }

  public ResponseEntity<Object> get(String id) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (ch.isPresent())
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(Map.of("chem", ch.get()));

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", "This chem doesn't exist"));
  }

  public ResponseEntity<Object> open(String id) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (!ch.isPresent())
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(Map.of("message", "This chem doesn't exist"));

    if (ch.get().getOpen())
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(Map.of("message", "This chem is already open"));

    ch.get().setOpen(true);

    ch.get().getOps().add(new Operation(
        "Open",
        ch.get().getCurrentLocation(),
        ch.get().getAmount(),
        LocalDateTime.now()));

    chemRepository.save(ch.get());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(Map.of("message", "The chem is opened successfully"));
  }

  public ResponseEntity<Object> close(String id) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (!ch.isPresent())
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(Map.of("message", "This chem doesn't exist"));

    if (!ch.get().getOpen())
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(Map.of("message", "This chem is already closed"));

    ch.get().setOpen(false);

    ch.get().getOps().add(new Operation(
        "Close",
        ch.get().getCurrentLocation(),
        ch.get().getAmount(),
        LocalDateTime.now()));

    chemRepository.save(ch.get());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(Map.of("message", "The chem is closed successfully"));
  }

  public ResponseEntity<Object> move(String id, String place) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (!ch.isPresent())
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(Map.of("message", "This chem doesn't exist"));

    if (ch.get().getCurrentLocation().equals(place))
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(Map.of("message", "The chem is already in " + place));

    String oldPlace = ch.get().getCurrentLocation();
    ch.get().setCurrentLocation(place);

    ch.get().getOps().add(new Operation(
        "Move",
        ch.get().getCurrentLocation(),
        ch.get().getAmount(),
        LocalDateTime.now()));

    chemRepository.save(ch.get());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(Map.of("message", "The chem is moved from " + oldPlace + " to " + place));
  }

  public ResponseEntity<Object> consume(String id, Integer amount) {
    Optional<ChemModel> ch = chemRepository.findById(id);
    if (!ch.isPresent())
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(Map.of("message", "This chem doesn't exist"));

    if (!ch.get().getOpen())
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(Map.of("message", "This chem is closed and you can't consume from it right now"));

    if (ch.get().getAmount() < amount)
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(Map.of("message", "The actual amount is less than the amount you need to consume"));

    ch.get().setAmount(ch.get().getAmount() - amount);

    ch.get().getOps().add(new Operation(
        "Consume",
        ch.get().getCurrentLocation(),
        amount,
        LocalDateTime.now()));

    chemRepository.save(ch.get());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(Map.of("message", "You have consumed " + amount + " letters successfully"));

  }
}
