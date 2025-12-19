// GenieTWP.java - New entity
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHECKING_TWP", schema = "genie")
public class GenieTWP {
    @Id
    @Column(name = "CHECKING_TWP_ID")
    private Long checkingTwpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TWP_ID")
    private TWP twp;
}