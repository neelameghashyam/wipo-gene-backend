package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OFFERING_NOTE", schema = "genie")
public class OfferingNote {
    @Id
    @Column(name = "OFFERING_NOTE_ID")
    private Long offeringNoteId;
    
    @Column(name = "OFFERING_ID")
    private Long offeringId;
    
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