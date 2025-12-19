package org.upov.genie.domain.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "AUTHORITY")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Authority {

    @Id
    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "AUTHORITY_CODE", length = 10)
    private String authorityCode;

    @Column(name = "AUTHORITY_NAME", length = 200)
    private String authorityName;

    @Column(name = "AUTHORITY_SEQUENCE")
    private Long authoritySequence;

    @Column(name = "PROTECTION_ID")
    private Long protectionId;

    @Column(name = "GROUP_INDICATOR", length = 1)
    private String groupIndicator;

    @Column(name = "UNASSIGNED_AUTHORITY", length = 1)
    private String unassignedAuthority;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "CREATE_USER", length = 30)
    private String createUser;

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(name = "UPDATE_USER", length = 30)
    private String updateUser;

    @Column(name = "ADMINISTRATIVE_WEB_ADDRESS", length = 1000)
    private String administrativeWebAddress;

    @Column(name = "LAW_WEB_ADDRESS", length = 1000)
    private String lawWebAddress;

    
    @Transient
    private String authorityType;
}