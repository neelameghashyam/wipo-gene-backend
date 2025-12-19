// GenieFamily.java - New entity
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_FAMILY", schema = "genie")
public class GenieFamily {
    @Id
    @Column(name = "GENIE_FAMILY_ID")
    private Long genieFamilyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    private Family family;
}