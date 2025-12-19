// TWP.java - New entity
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TWP", schema = "genie")
public class TWP {
    @Id
    @Column(name = "TWP_ID")
    private Long twpId;

    @Column(name = "TWP_CODE")
    private String twpCode;

    @Column(name = "TWP_NAME")
    private String twpName;
}