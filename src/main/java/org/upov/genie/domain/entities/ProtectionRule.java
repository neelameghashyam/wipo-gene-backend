package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROTECTION_RULE", schema = "genie")
public class ProtectionRule {
    
    @Id
    @Column(name = "PROTECTION_RULE_ID")
    private Long protectionRuleId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "RULE_TYPE_ID")
    private Long ruleTypeId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "PROTECTION_ID")
    private Long protectionId;

    @Column(name = "FAMILY_ID")
    private Long familyId;

    @Column(name = "PROTECTION_ACTIVE")
    private String protectionActive;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}