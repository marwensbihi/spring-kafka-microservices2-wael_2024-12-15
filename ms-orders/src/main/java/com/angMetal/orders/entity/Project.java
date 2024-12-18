package com.angMetal.orders.entity;

import com.angMetal.orders.enums.StatutProjet;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projetID;

    @Column(nullable = false)
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters.")
    private String nomProjet;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date is required.")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private StatutProjet statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientID", nullable = false)
    private Client client;

    @OneToMany
    private List<Timesheet> timeSheets;

    @OneToMany
    private List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
