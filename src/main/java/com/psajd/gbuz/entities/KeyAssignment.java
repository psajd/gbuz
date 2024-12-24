package com.psajd.gbuz.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "key_assignment")
public class KeyAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate assignmentDate;

    @ManyToOne(optional = false)
    private Certificate certificate;

    @ManyToOne(optional = false)
    private KeyCard keyCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public KeyCard getKeyCard() {
        return keyCard;
    }

    public void setKeyCard(KeyCard keyCard) {
        this.keyCard = keyCard;
    }
}
