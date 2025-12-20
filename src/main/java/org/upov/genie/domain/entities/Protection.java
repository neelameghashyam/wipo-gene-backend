package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROTECTION", schema = "genie")
public class Protection {
    
    @Id
    @Column(name = "PROTECTION_ID")
    private Long protectionId;

    @Column(name = "PROTECTION_NAME")
    private String protectionName;

    @Column(name = "PROTECTION_SEQUENCE")
    private Integer protectionSequence;

    @Column(name = "UNASSIGNED_PROTECTION")
    private String unassignedProtection;

    @Column(name = "IS_PROTECTION_MODIFIABLE")
    private String isProtectionModifiable;

    @Column(name = "AUTOMATIC_PROTECTION")
    private String automaticProtection;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "IS_ALL_SPECIES_PROTECTION")
    private String isAllSpeciesProtection;

    @Column(name = "FAMILY_PROTECTION")
    private String familyProtection;

    @Column(name = "ALL_SPECIES_PROTECTION")
    private String allSpeciesProtection;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}