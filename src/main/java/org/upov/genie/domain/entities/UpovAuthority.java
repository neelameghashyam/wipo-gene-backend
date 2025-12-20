package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(name = "AUTHORITY_SEQUENCE")
    private Integer authoritySequence;

    @Column(name = "AUTHORITY_TYPE_ID")
    private Long authorityTypeId;

    @Column(name = "ADMINISTRATIVE_WEB_ADDRESS")
    private String administrativeWebAddress;

    @Column(name = "LAW_WEB_ADDRESS")
    private String lawWebAddress;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<AuthorityName> names;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_TYPE_ID", insertable = false, updatable = false)
    private AuthorityType authorityType;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<GenieSpeciesProtection> protections;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}