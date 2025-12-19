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
    private Integer usageId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private UpovAuthority authority;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID")
    private ProtectionDerivation derivation;
    
    @Column(name = "EXPERIENCE_STRING")
    private String experienceString;
    
    @Column(name = "NOTE_STRING")
    private String noteString;
    
    @Column(name = "WEB_ADDRESS")
    private String webAddress;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "WITH_NOTES")
    private String withNotes;
}

