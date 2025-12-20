package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_PROTECTION", schema = "genie")
public class GenieSpeciesProtection {
    
    @Id
    @Column(name = "GENIE_PROTECTION_ID")
    private Long genieProtectionId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "PROTECTION_ID")
    private Integer protectionId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "NOTE_STRING")
    private String noteString;

    @Column(name = "PROTECTION_ACTIVE")
    private String protectionActive;

    @Column(name = "PROTECTION_MODIFIED")
    private String protectionModified;

    @Column(name = "PROTECTION_RULE_ID")
    private Long protectionRuleId;

    @Column(name = "WITH_NOTES")
    private String withNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROTECTION_ID", insertable = false, updatable = false)
    private Protection protection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;

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