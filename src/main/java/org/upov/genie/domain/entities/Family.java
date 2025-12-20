package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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
    private Long categoryId;  // ADD THIS FIELD

    @Column(name = "FAMILY_SORT")
    private String familySort;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}