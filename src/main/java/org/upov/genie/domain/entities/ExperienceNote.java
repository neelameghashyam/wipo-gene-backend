package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPERIENCE_NOTE", schema = "genie")
public class ExperienceNote {
    
    @Id
    @Column(name = "EXPERIENCE_NOTE_ID")
    private Long experienceNoteId;

    @Column(name = "EXPERIENCE_ID")
    private Long experienceId;

    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}