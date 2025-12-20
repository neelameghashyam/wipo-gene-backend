package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPECIES_UTILIZATION", schema = "genie")
public class SpeciesUtilization {
    
    @Id
    @Column(name = "SPECIES_UTILIZATION_ID")
    private Long speciesUtilizationId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "UTILIZING_AUTHORITY_ID")
    private Long utilizingAuthorityId;

    @Column(name = "PROVIDING_AUTHORITY_ID")
    private Long providingAuthorityId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "NOTE_STRING")
    private String noteString;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTILIZING_AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority utilizingAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDING_AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority providingAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;

    // ADD THIS RELATIONSHIP
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