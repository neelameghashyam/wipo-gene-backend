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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private UpovAuthority authority;
    
    @Column(name = "PROTECTION_ID")
    private Integer protectionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID")
    private ProtectionDerivation derivation;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "PROTECTION_ACTIVE")
    private String protectionActive;
    
    @Column(name = "PROTECTION_MODIFIED")
    private String protectionModified;
    
    @Column(name = "PROTECTION_STRING")
    private String protectionString;
    
    @Column(name = "NOTE_STRING")
    private String noteString;
    
    @Column(name = "PROTECTION_RULE_ID")
    private Integer protectionRuleId;
    
    @Column(name = "WITH_NOTES")
    private String withNotes;
}

