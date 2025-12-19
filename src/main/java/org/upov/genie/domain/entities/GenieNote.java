package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTE", schema = "genie")
public class GenieNote {
    @Id
    @Column(name = "NOTE_ID")
    private Long noteId;
    
    @Column(name = "NOTE_CONTENT")
    private String noteContent;
    
    @Column(name = "NOTE_SEQUENCE")
    private String noteSequence;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "UNASSIGNED_NOTE")
    private String unassignedNote;
    
    @Column(name = "NOTE_CATEGORY")
    private String noteCategory;
    
    @Column(name = "NOTE_ALL")
    private String noteAll;
}

