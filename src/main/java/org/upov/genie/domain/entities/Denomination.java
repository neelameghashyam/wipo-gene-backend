// Denomination.java
package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DENOMINATION", schema = "genie")
public class Denomination {
    
    @Id
    @Column(name = "DENOMINATION_ID")
    private Long denominationId;

    @Column(name = "DENOMINATION_NAME")
    private String denominationName;

    @Column(name = "NO_DENOMINATION")
    private String noDenomination;

    @Column(name = "SUPER_DENOMINATION")
    private String superDenomination;

    @Column(name = "SORT_CODE")
    private Integer sortCode;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}