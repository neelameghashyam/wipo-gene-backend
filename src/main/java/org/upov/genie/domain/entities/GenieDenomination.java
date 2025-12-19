// GenieDenomination.java
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_DENOMINATION", schema = "genie")
public class GenieDenomination {
    
    @Id
    @Column(name = "GENIE_DENOMINATION_ID")
    private Long genieDenominationId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "DENOMINATION_ID")
    private Long denominationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DENOMINATION_ID", insertable = false, updatable = false)
    private Denomination denomination;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}