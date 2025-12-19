// Family.java - New entity
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FAMILY", schema = "genie")
public class Family {
    @Id
    @Column(name = "FAMILY_ID")
    private Long familyId;

    @Column(name = "FAMILY_NAME")
    private String familyName;

    @Column(name = "CATEGORY_ID")
    private Integer categoryId;
}