// GenieTWP.java
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHECKING_TWP", schema = "genie")
public class GenieTWP {
    
    @Id
    @Column(name = "CHECKING_TWP_ID")
    private Long checkingTwpId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "TWP_ID")
    private Long twpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TWP_ID", insertable = false, updatable = false)
    private TWP twp;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}