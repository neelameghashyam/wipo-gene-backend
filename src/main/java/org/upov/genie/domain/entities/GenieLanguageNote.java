package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LANGUAGE_NOTE", schema = "genie")
public class GenieLanguageNote {
    @Id
    @Column(name = "LANGUAGE_NOTE_ID")
    private Long languageNoteId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_ID")
    private GenieNote note;
    
    @Column(name = "LANGUAGE_ID")
    private Integer languageId;
    
    @Column(name = "NOTE_CONTENT")
    private String noteContent;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
}