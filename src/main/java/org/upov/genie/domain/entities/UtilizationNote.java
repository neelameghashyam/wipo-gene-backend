package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UTILIZATION_NOTE", schema = "genie")
public class UtilizationNote {
    @Id
    @Column(name = "UTILIZATION_NOTE_ID")
    private Long utilizationNoteId;
    
    @Column(name = "UTILIZATION_ID")
    private Long utilizationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_ID")
    private GenieNote note;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
}