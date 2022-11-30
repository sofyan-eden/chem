package com.example.demo.chem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChemRepository extends MongoRepository<ChemModel, String> {
  
}
