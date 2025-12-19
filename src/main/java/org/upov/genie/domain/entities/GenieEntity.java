package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GENIE", schema = "genie")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GenieEntity {

    @Id
    @Column(name = "GENIE_ID")
    private String genieId;              

    @Column(name = "UPOV_CODE")
    private String upovCode;

    @Column(name = "CODE_ACTIVE")
    private String codeActive;
}