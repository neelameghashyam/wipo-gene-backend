package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORITY_TYPE", schema = "genie")
public class AuthorityType {
    @Id
    @Column(name = "AUTHORITY_TYPE_ID")
    private Long authorityTypeId;
    
    @Column(name = "AUTHORITY_TYPE")
    private String authorityType;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
}