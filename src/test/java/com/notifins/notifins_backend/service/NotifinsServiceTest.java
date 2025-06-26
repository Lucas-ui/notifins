package com.notifins.notifins_backend.service;

import com.notifins.notifins_backend.application.service.NotifinsService;
import com.notifins.notifins_backend.domain.entities.AbonnementNOTIFINS;
import com.notifins.notifins_backend.domain.entities.Operation;
import com.notifins.notifins_backend.domain.enums.StatutAbonnement;
import com.notifins.notifins_backend.domain.enums.StatutOperation;
import com.notifins.notifins_backend.infrastucture.repository.AbonnementRepository;
import com.notifins.notifins_backend.infrastucture.repository.OperationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotifinsServiceTest {

    @Mock
    private AbonnementRepository abonnementRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private NotifinsService notifinsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDemanderInscriptionEtTraitementReponse() {
        // 1. Préparation des données
        AbonnementNOTIFINS abonne = new AbonnementNOTIFINS();
        abonne.setNir("1234567890123");
        abonne.setNom("Rolland");
        abonne.setPrenom("Lucas");
        abonne.setEmail("lucas@example.com");
        abonne.setTelephone("0601020304");
        abonne.setDateNaissance(LocalDate.of(2001, 06, 13));
        abonne.setStatut(StatutAbonnement.EN_COURS);

        Operation operation = new Operation();
        operation.setId(1L);
        operation.setStatut(StatutOperation.ENVOYE);
        operation.setAbonnement(abonne);
        abonne.setOperations(new ArrayList<>(List.of(operation)));


        // 2. Simulation des repositories
        when(abonnementRepository.save(any())).thenReturn(abonne);
        when(operationRepository.save(any())).thenReturn(operation);
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        // 3. Appel de la méthode demanderInscription
        notifinsService.demanderInscription(abonne);

        // 4. Appel de la méthode traiterReponseNotifins avec succès = true
        notifinsService.traiterReponseNotifins(1L, true, null);

        // 5. Vérifications
        assertEquals(StatutOperation.SUCCES, operation.getStatut());
        assertEquals(StatutAbonnement.INSCRIT, abonne.getStatut());

        // 6. Vérifie que les sauvegardes ont été appelées
        verify(abonnementRepository, atLeastOnce()).save(abonne);
        verify(operationRepository).save(operation);
    }
}
