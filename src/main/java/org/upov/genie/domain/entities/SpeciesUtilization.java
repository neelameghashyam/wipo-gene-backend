package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILIZATION", schema = "genie")
public class SpeciesUtilization {
    @Id
    @Column(name = "UTILIZATION_ID")
    private Long utilizationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;
    
    @Column(name = "UTILIZING_STRING")
    private String utilizingString;
    
    @Column(name = "PROVIDING_STRING")
    private String providingString;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID")
    private ProtectionDerivation derivation;
    
    @Column(name = "EXCLUDE_UTILIZATION")
    private String excludeUtilization;
    
    @Column(name = "UTILIZATION_RULE_ID")
    private Integer utilizationRuleId;
    
    @Column(name = "NOTE_STRING")
    private String noteString;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDING_AUTHORITY_ID")
    private UpovAuthority providingAuthority;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTILIZING_AUTHORITY_ID")
    private UpovAuthority utilizingAuthority;
    
    @Column(name = "WITH_NOTES")
    private String withNotes;
}

