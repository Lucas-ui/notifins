package com.notifins.notifins_backend.application.service;

import com.notifins.notifins_backend.domain.entities.AbonnementNOTIFINS;
import com.notifins.notifins_backend.domain.entities.Operation;
import com.notifins.notifins_backend.domain.enums.StatutAbonnement;
import com.notifins.notifins_backend.domain.enums.StatutOperation;
import com.notifins.notifins_backend.infrastucture.repository.AbonnementRepository;
import com.notifins.notifins_backend.infrastucture.repository.OperationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotifinsService {

    private final AbonnementRepository abonnementRepository;
    private final OperationRepository operationRepository;

    public NotifinsService(AbonnementRepository abonnementRepository, OperationRepository operationRepository) {
        this.abonnementRepository = abonnementRepository;
        this.operationRepository = operationRepository;
    }

    public AbonnementNOTIFINS demanderInscription(AbonnementNOTIFINS nouvelAbonnement) {
        // Vérifie si l'abonné existe déjà
        AbonnementNOTIFINS abonnement = abonnementRepository.findById(nouvelAbonnement.getNir()).orElse(null);

        if (abonnement == null) {
            // Première inscription
            abonnement = nouvelAbonnement;
            abonnement.setStatut(StatutAbonnement.EN_COURS);

            Operation inscription = new Operation();
            inscription.setDate(LocalDate.now());
            inscription.setStatut(StatutOperation.EN_COURS);

            abonnement.ajouterOperation(inscription);
            abonnementRepository.save(abonnement);

        } else if (abonnement.getStatut() == StatutAbonnement.DESINSCRIT) {
            // Réinscription possible uniquement depuis état DESINSCRIT
            abonnement.setStatut(StatutAbonnement.EN_COURS);

            Operation inscription = new Operation();
            inscription.setDate(LocalDate.now());
            inscription.setStatut(StatutOperation.EN_COURS);

            abonnement.ajouterOperation(inscription);
            abonnementRepository.save(abonnement);

        } else {
            throw new IllegalStateException("Inscription impossible");
        }

        return abonnement;
    }

    public AbonnementNOTIFINS demanderDesinscription(String nir) {
        AbonnementNOTIFINS abonnement = abonnementRepository.findById(nir)
            .orElseThrow(() -> new IllegalArgumentException("Aucun abonnement trouvé avec le NIR : " + nir));

        if (abonnement.getStatut() != StatutAbonnement.INSCRIT) {
            throw new IllegalStateException("Désinscription impossible depuis l'état : " + abonnement.getStatut());
        }

        // Vérifie qu'aucune autre opération n'est en cours
        boolean hasOperationEnCours = abonnement.getOperations().stream()
            .anyMatch(op -> op.getStatut() == StatutOperation.EN_COURS);
        
        if (hasOperationEnCours) {
            throw new IllegalStateException("Une autre opération est déjà en cours");
        }

        // Création de l'opération de désinscription
        Operation desinscription = new Operation();
        desinscription.setDate(LocalDate.now());
        desinscription.setStatut(StatutOperation.EN_COURS);

        abonnement.ajouterOperation(desinscription);
        abonnement.setStatut(StatutAbonnement.EN_COURS);

        return abonnementRepository.save(abonnement);
    }

    public void traiterReponseNotifins(Long operationId, boolean success, String codeErreur) {
        Operation operation = operationRepository.findById(operationId)
            .orElseThrow(() -> new IllegalArgumentException("Opération introuvable"));

        AbonnementNOTIFINS abonnement = operation.getAbonnement();

        if (!operation.getStatut().equals(StatutOperation.ENVOYE)) {
            throw new IllegalStateException("Seules les opérations envoyées peuvent être traitées");
        }

        if (success) {
            operation.setStatut(StatutOperation.SUCCES);

            // Détermine si c'était une inscription ou désinscription
            StatutAbonnement nouveauStatut = (abonnement.getStatut() == StatutAbonnement.EN_COURS && abonnement.getOperations().size() == 1)
                    ? StatutAbonnement.INSCRIT
                    : StatutAbonnement.DESINSCRIT;

            abonnement.setStatut(nouveauStatut);

        } else {
            operation.setStatut(StatutOperation.ECHEC);
            operation.setCodeErreur(codeErreur);
            abonnement.setStatut(StatutAbonnement.ECHEC);
        }

        operationRepository.save(operation);
        abonnementRepository.save(abonnement);
    }

    public OperationRepository getOperationRepository() {
        return this.operationRepository;
    }

    public List<AbonnementNOTIFINS> getAllAbonnements() {
        return abonnementRepository.findAll();
    }
}
