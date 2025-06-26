package com.notifins.notifins_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.notifins.notifins_backend.application.service.NotifinsService;
import com.notifins.notifins_backend.domain.entities.AbonnementNOTIFINS;
import com.notifins.notifins_backend.domain.entities.Operation;
import com.notifins.notifins_backend.domain.enums.StatutOperation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@SpringBootApplication
public class NotifinsBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotifinsBackendApplication.class, args);
	}

    @Bean
    @Transactional
    public CommandLineRunner testNotifins(NotifinsService notifinsService) {
        return args -> {
            System.out.println("Début du test");

            // Création de l’abonné
            AbonnementNOTIFINS abonne = new AbonnementNOTIFINS();
            abonne.setNir("1234567890123");
            abonne.setNom("Rolland");
            abonne.setPrenom("Lucas");
            abonne.setEmail("lucas7500@example.com");
            abonne.setTelephone("0624257943");
            abonne.setDateNaissance(LocalDate.of(2001, 06, 13));

            try {
                // Demande d'inscription
                notifinsService.demanderInscription(abonne);
                System.out.println("Inscription demandée");

                // Opération créée
                Operation op = abonne.getOperations().get(0);
                op.setStatut(StatutOperation.ENVOYE);
                op.setDateEnvoi(LocalDate.now());
                notifinsService.getOperationRepository().save(op);

                System.out.println("Opération marquée comme ENVOYÉE");

                // Réponse positive
                notifinsService.traiterReponseNotifins(op.getId(), true, null);
                System.out.println("Réponse traitée (succès)");

            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            System.out.println("Fin du test");
        };
    }

}
