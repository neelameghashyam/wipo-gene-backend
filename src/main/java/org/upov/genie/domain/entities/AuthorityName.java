package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORITY_NAME", schema = "genie")
public class AuthorityName {
    
    @Id
    @Column(name = "AUTHORITY_NAME_ID")
    private Long authorityNameId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    @Column(name = "LANGUAGE_ID")
    private Integer languageId;

    @Column(name = "AUTHORITY_NAME")
    private String authorityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private UpovAuthority authority;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}