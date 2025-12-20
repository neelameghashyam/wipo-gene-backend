package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_GUIDELINE", schema = "genie")
public class GenieGuideline {
    
    @Id
    @Column(name = "GENIE_GUIDELINE_ID")
    private Long genieGuidelineId;

    @Column(name = "GENIE_ID")
    private Long genieId;

    @Column(name = "GUIDELINE_ID")
    private Long guidelineId;

    @Column(name = "DERIVATION_ID")
    private Long derivationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUIDELINE_ID", insertable = false, updatable = false)
    private Guideline guideline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DERIVATION_ID", insertable = false, updatable = false)
    private Derivation derivation;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}