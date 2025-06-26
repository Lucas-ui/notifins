package com.notifins.notifins_backend.infrastucture.repository;

import com.notifins.notifins_backend.domain.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

// Cette fonction reste vide mais comporte quand même des méthodes
// findAll(), findById(), save(), deleteById() => grâce à Spring Data JPA
public interface OperationRepository extends JpaRepository<Operation, Long> {
   
}
