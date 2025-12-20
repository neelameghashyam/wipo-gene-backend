package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OFFERING", schema = "genie")
public class Offering {
    
    @Id
    @Column(name = "OFFERING_ID")
    private Long offeringId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "OFFERING_STRING")
    private String offeringString;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "EO_DESIGNATION")
    private String eoDesignation;

    @Column(name = "NOTE_STRING")
    private String noteString;

    @Column(name = "WITH_NOTES")
    private String withNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}