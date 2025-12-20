package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROTECTION_NAME", schema = "genie")
public class ProtectionName {
    
    @Id
    @Column(name = "PROTECTION_NAME_ID")
    private Long protectionNameId;

    @Column(name = "PROTECTION_ID")
    private Long protectionId;

    @Column(name = "LANGUAGE_ID")
    private Integer languageId;

    @Column(name = "PROTECTION_NAME")
    private String protectionName;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}