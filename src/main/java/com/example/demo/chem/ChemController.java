package com.example.demo.chem;

import java.util.ArrayList;
import java.util.List;

import javax.management.MXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@RestController
@Validated
@RequestMapping("api/v1/chems")
public class ChemController {
  
  private final ChemService chemService;

  @Autowired
  public ChemController(ChemService chemService) {
    this.chemService = chemService;
  }

  @GetMapping("/")
  public List<ChemModel> getAll() {
    return chemService.getAll();
  }

  @PostMapping("/")
  public ChemModel create(@RequestBody ChemModel chem) {
    chem.setOps(new ArrayList<>());
    return chemService.create(chem);
  }

  @GetMapping("/activate/{id}")
  public ResponseEntity<Object> activate(@PathVariable String id) {
    return chemService.setState(true, id);
  }

  @GetMapping("/deactivate/{id}")
  public ResponseEntity<Object> deactivate(@PathVariable String id) {
    return chemService.setState(false, id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> get(@PathVariable String id) {
    return chemService.get(id);
  }

  @GetMapping("/open/{id}")
  public ResponseEntity<Object> open(@PathVariable String id) {
    return chemService.open(id);
  }

  @GetMapping("/close/{id}")
  public ResponseEntity<Object> close(@PathVariable String id) {
    return chemService.close(id);
  }

  @GetMapping("/move/{id}")
  public ResponseEntity<Object> move(
      @PathVariable String id, 
      @RequestParam("place") @NotEmpty(message = "The new place is required") String place
    ) {
      return chemService.move(id, place);
  }

  @PostMapping("/consume")
  public ResponseEntity<Object> consume(
    @RequestParam("id") String id,
    @RequestParam("amount") @Min(value = 1, message = "The value must be greater than 0") Integer amount
  ) {
    System.out.println(id);
    return chemService.consume(id, amount);
  }
}
