package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPERIENCE", schema = "genie")
public class SpeciesExperience {
    
    @Id
    @Column(name = "EXPERIENCE_ID")
    private Long experienceId;

    @Column(name = "USAGE_ID")
    private Long usageId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "EXPERIENCE_STRING")
    private String experienceString;

    @Column(name = "NOTE_STRING")
    private String noteString;

    @Column(name = "WEB_ADDRESS")
    private String webAddress;

    @Column(name = "WITH_NOTES")
    private String withNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;  // CORRECT: Use Derivation, not ProtectionDerivation

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