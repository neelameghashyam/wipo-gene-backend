package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DERIVATION_NAME", schema = "genie")
public class DerivationName {
    
    @Id
    @Column(name = "DERIVATION_NAME_ID")
    private Long derivationNameId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @Column(name = "LANGUAGE_ID")
    private Integer languageId;

    @Column(name = "DERIVATION_NAME")
    private String derivationName;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}