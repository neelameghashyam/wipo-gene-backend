package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORITY", schema = "genie")
public class UpovAuthority {
    @Id
    @Column(name = "AUTHORITY_ID")
    private Long authorityId;
    
    @Column(name = "AUTHORITY_CODE")
    private String authorityCode;
    
    @Column(name = "AUTHORITY_NAME")
    private String authorityName;
    
    @Column(name = "AUTHORITY_SEQUENCE")
    private Integer authoritySequence;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "GROUP_INDICATOR")
    private String groupIndicator;
    
    @Column(name = "LAW_WEB_ADDRESS")
    private String lawWebAddress;
    
    @Column(name = "PROTECTION_ID")
    private Integer protectionId;
    
    @Column(name = "UNASSIGNED_AUTHORITY")
    private String unassignedAuthority;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "ADMINISTRATIVE_WEB_ADDRESS")
    private String administrativeWebAddress;
}

