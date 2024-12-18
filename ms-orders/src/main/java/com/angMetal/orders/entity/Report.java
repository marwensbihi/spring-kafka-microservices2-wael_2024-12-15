package com.angMetal.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    private String typeRapport;

    @Temporal(TemporalType.DATE)
    private Date periodeDebut;

    @Temporal(TemporalType.DATE)
    private Date periodeFin;

    @Column(name = "budget")
    @NonNull
    private Double budget;

}