package com.notifins.notifins_backend.infrastucture.repository;

import com.notifins.notifins_backend.domain.entities.AbonnementNOTIFINS;
import org.springframework.data.jpa.repository.JpaRepository;

// Cette fonction reste vide mais comporte quand même des méthodes
// findAll(), findById(), save(), deleteById() => grâce à Spring Data JPA
public interface AbonnementRepository extends JpaRepository<AbonnementNOTIFINS, String> {

}
