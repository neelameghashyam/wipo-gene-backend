package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GUIDELINE", schema = "genie")
public class Guideline {
    
    @Id
    @Column(name = "GUIDELINE_ID")
    private Long guidelineId;

    @Column(name = "GUIDELINE_CODE")
    private String guidelineCode;

    @Column(name = "GUIDELINE_NAME")
    private String guidelineName;

    @Column(name = "GUIDELINE_STATUS_ID")
    private Long guidelineStatusId;

    @Column(name = "GUIDELINE_SORT")
    private String guidelineSort;

    @Column(name = "UNASSIGNED_GUIDELINE")
    private String unassignedGuideline;

    @Column(name = "WEB_ADDRESS")
    private String webAddress;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}