package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILIZATION_RULE", schema = "genie")
public class UtilizationRule {
    
    @Id
    @Column(name = "UTILIZATION_RULE_ID")
    private Long utilizationRuleId;

    @Column(name = "RULE_NAME")
    private String ruleName;

    @Column(name = "RULE_SEQUENCE")
    private Integer ruleSequence;

    @Column(name = "UTILIZING_AUTHORITY_ID")
    private Long utilizingAuthorityId;

    @Column(name = "PROVIDING_AUTHORITY_ID")
    private Long providingAuthorityId;

    @Column(name = "EXPERIENCE_AUTHORITY_ID")
    private Long experienceAuthorityId;

    @Column(name = "PROTECTION_AUTHORITY_ID")
    private Long protectionAuthorityId;

    @Column(name = "ALL_UPOV_CODES")
    private String allUpovCodes;

    @Column(name = "EXCEPTION_LIST")
    private String exceptionList;

    @Column(name = "EXPERIENCE_CONDITION")
    private String experienceCondition;

    @Column(name = "PROTECTION_CONDITION")
    private String protectionCondition;

    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "UNASSIGNED_RULE")
    private String unassignedRule;

    @Column(name = "EXCLUDE_UTILIZATION")
    private String excludeUtilization;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}