package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORITY_TYPES", schema = "genie")
public class AuthorityTypes {
    @Id
    @Column(name = "AUTHORITY_TYPES_ID")
    private Long authorityTypesId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private UpovAuthority authority;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_TYPE_ID")
    private AuthorityType authorityType;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "ADMINISTRATIVE_WEB_ADDRESS")
    private String administrativeWebAddress;
}