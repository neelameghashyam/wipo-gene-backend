// GenieFamily.java
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_FAMILY", schema = "genie")
public class GenieFamily {
    
    @Id
    @Column(name = "GENIE_FAMILY_ID")
    private Long genieFamilyId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "FAMILY_ID")
    private Long familyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID", insertable = false, updatable = false)
    private Family family;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}