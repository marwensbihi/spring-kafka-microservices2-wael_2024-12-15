package com.angMetal.orders.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_id")
    private Long id;

    private int heuresTravaillees;

    @Temporal(TemporalType.DATE)
    private Date date;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "report_id")
    private Report report;
}
