package com.notifins.notifins_backend.interfaces.controller;

import com.notifins.notifins_backend.application.service.NotifinsService;
import com.notifins.notifins_backend.domain.entities.AbonnementNOTIFINS;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final NotifinsService notifinsService;

    public AbonnementController(NotifinsService notifinsService) {
        this.notifinsService = notifinsService;
    }

    // Création / inscription d'un abonnement
    @PostMapping("/inscription")
    public AbonnementNOTIFINS inscrire(@RequestBody AbonnementNOTIFINS nouvelAbonnement) {
        return notifinsService.demanderInscription(nouvelAbonnement);
    }

    @GetMapping
    public List<AbonnementNOTIFINS> allAbonnements() {
        return notifinsService.getAllAbonnements();
    }
}
