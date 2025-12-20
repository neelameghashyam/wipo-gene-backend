package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILIZATION", schema = "genie")
public class Utilization {
    
    @Id
    @Column(name = "UTILIZATION_ID")
    private Long utilizationId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "UTILIZING_AUTHORITY_ID")
    private Long utilizingAuthorityId;

    @Column(name = "PROVIDING_AUTHORITY_ID")
    private Long providingAuthorityId;

    @Column(name = "UTILIZING_STRING")
    private String utilizingString;

    @Column(name = "PROVIDING_STRING")
    private String providingString;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "EXCLUDE_UTILIZATION")
    private String excludeUtilization;

    @Column(name = "UTILIZATION_RULE_ID")
    private Long utilizationRuleId;

    @Column(name = "NOTE_STRING")
    private String noteString;

    @Column(name = "WITH_NOTES")
    private String withNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTILIZING_AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority utilizingAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDING_AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority providingAuthority;

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