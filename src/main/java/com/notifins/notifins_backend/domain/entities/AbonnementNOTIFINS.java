package com.notifins.notifins_backend.domain.entities;

import com.notifins.notifins_backend.domain.enums.StatutAbonnement;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AbonnementNOTIFINS {

    @Id
    private String nir;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    // Un seul abonnement peut être lié à plusieurs opérations / Tout ce qui est fait sur AbonnementNOTIFINS est aussi fait sur les Operations associées
    @OneToMany(mappedBy = "abonnement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();

    public String getNir() {
        return nir;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutAbonnement getStatut() {
        return statut;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void ajouterOperation(Operation operation) {
        operations.add(operation);
        operation.setAbonnement(this);
    }
}
