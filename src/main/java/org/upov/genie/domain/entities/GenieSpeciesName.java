package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE_NAME", schema = "genie")
public class GenieSpeciesName {
    @Id
    @Column(name = "GENIE_NAME_ID")
    private Long genieNameId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENIE_ID")
    private GenieSpecies genieSpecies;
    
    @Column(name = "LANGUAGE_ID")
    private Integer languageId;
    
    @Column(name = "GENIE_NAME")
    private String genieName;
    
    @Column(name = "DEFAULT_NAME")
    private String defaultName;
    
    @Column(name = "STABALISED_NAME")
    private String stabalisedName;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
}