package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OFFERING_RULE", schema = "genie")
public class OfferingRule {
    @Id
    @Column(name = "OFFERING_RULE_ID")
    private Long offeringRuleId;
    
    @Column(name = "RULE_TYPE_ID")
    private Integer ruleTypeId;
    
    @Column(name = "FAMILY_ID")
    private Long familyId;
    
    @Column(name = "AUTHORITY_ID")
    private Long authorityId;
    
    @Column(name = "DERIVATION_ID")
    private Integer derivationId;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "EO_DESIGNATION")
    private String eoDesignation;
}