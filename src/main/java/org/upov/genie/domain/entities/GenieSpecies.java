package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENIE", schema = "genie")
public class GenieSpecies {
    @Id
    @Column(name = "GENIE_ID")
    private Long genieId;
    
    @Column(name = "UPOV_CODE")
    private String upovCode;
    
    @Column(name = "GENIE_NAME")
    private String genieName;
    
    @Column(name = "GENUS_CODE")
    private String genusCode;
    
    @Column(name = "SPECIES_CODE")
    private String speciesCode;
    
    @Column(name = "SUB_SPECIES_CODE_1")
    private String subSpeciesCode1;
    
    @Column(name = "SUB_SPECIES_CODE_2")
    private String subSpeciesCode2;
    
    @Column(name = "SUB_SPECIES_CODE_3")
    private String subSpeciesCode3;
    
    @Column(name = "HYBRID_FLAG")
    private String hybridFlag;
    
    @Column(name = "CHECKING_TYPE_ID")
    private Integer checkingTypeId;
    
    @Column(name = "EXPERIENCE_STRING")
    private String experienceString;
    
    @Column(name = "ROOT_EXPERIENCE")
    private String rootExperience;
    
    @Column(name = "TWP_STRING")
    private String twpString;
    
    @Column(name = "AUTHORITY_STRING")
    private String authorityString;
    
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    @Column(name = "UPDATE_USER")
    private String updateUser;
    
    @Column(name = "GENIE_CODE")
    private String genieCode;
    
    @Column(name = "ROOT_PROTECTION")
    private String rootProtection;
    
    @Column(name = "CODE_ACTIVE")
    private String codeActive;
    
    @Column(name = "CODE_GENERATED")
    private String codeGenerated;
    
    @Column(name = "ROOT_EXAMINATION")
    private String rootExamination;
    
    @Column(name = "ROOT_UTILIZATION")
    private String rootUtilization;
    
    @OneToMany(mappedBy = "genieSpecies", fetch = FetchType.LAZY)
    private List<GenieSpeciesName> names;
    
    @OneToMany(mappedBy = "genieSpecies", fetch = FetchType.LAZY)
    private List<GenieSpeciesProtection> protections;
    
    @OneToMany(mappedBy = "genieSpecies", fetch = FetchType.LAZY)
    private List<SpeciesExperience> experiences;
    
    @OneToMany(mappedBy = "genieSpecies", fetch = FetchType.LAZY)
    private List<SpeciesOffering> offerings;
    
    @OneToMany(mappedBy = "genieSpecies", fetch = FetchType.LAZY)
    private List<SpeciesUtilization> utilizations;
}