package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPECIES_OFFERING", schema = "genie")
public class SpeciesOffering {
    
    @Id
    @Column(name = "SPECIES_OFFERING_ID")
    private Long speciesOfferingId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "OFFERING_STRING")
    private String offeringString;

    @Column(name = "EO_DESIGNATION")
    private String eoDesignation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;

    // ADD THIS RELATIONSHIP - This is what was missing!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID", insertable = false, updatable = false)
    private GenieSpecies genieSpecies;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}