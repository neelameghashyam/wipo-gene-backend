package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OFFERING", schema = "genie")
public class SpeciesOffering {
    @Id
    @Column(name = "OFFERING_ID")
    private Long offeringId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private UpovAuthority authority;
    
    @Column(name = "OFFERING_STRING")
    private String offeringString;
    
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
    
    @Column(name = "EO_DESIGNATION")
    private String eoDesignation;
    
    @Column(name = "NOTE_STRING")
    private String noteString;
    
    @Column(name = "WITH_NOTES")
    private String withNotes;
}

