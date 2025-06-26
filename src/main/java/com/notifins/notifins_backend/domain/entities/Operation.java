package com.notifins.notifins_backend.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.notifins.notifins_backend.domain.enums.StatutOperation;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private StatutOperation statut;

    private String codeErreur;

    private LocalDate dateEnvoi;

    // Plusieurs opérations peuvent être liées à un AbonnementNOTIFINS
    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private AbonnementNOTIFINS abonnement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatutOperation getStatut() {
        return statut;
    }

    public void setStatut(StatutOperation statut) {
        this.statut = statut;
    }

    public String getCodeErreur() {
        return codeErreur;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public AbonnementNOTIFINS getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(AbonnementNOTIFINS abonnement) {
        this.abonnement = abonnement;
    }
}
