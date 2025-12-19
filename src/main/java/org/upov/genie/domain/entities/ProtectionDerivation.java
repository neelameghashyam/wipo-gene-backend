package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DERIVATION", schema = "genie")
public class ProtectionDerivation {
    @Id
    @Column(name = "DERIVATION_ID")
    private Integer derivationId;
    
    @Column(name = "DERIVATION_NAME")
    private String derivationName;
    
    @Column(name = "UNASSIGNED_DERIVATION")
    private String unassignedDerivation;
    
    @Column(name = "DERIVATION_SEQUENCE")
    private Integer derivationSequence;
    
    @Column(name = "DERIVATION_CODE")
    private String derivationCode;
    
    @Column(name = "DERIVATIONS_ACTIVE")
    private String derivationsActive;
    
    @Column(name = "ROOT_DERIVATION")
    private String rootDerivation;
    
    @Column(name = "DERIVATION_INDICATOR")
    private String derivationIndicator;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
}

