package org.upov.genie.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GENIE_HIERARCHY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenieHierarchy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genie_hierarchy_seq")
    @SequenceGenerator(name = "genie_hierarchy_seq", sequenceName = "GENIE_HIERARCHY_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PARENT_GENIE_ID")
    private Long parentGenieId;
    
    @Column(name = "CHILD_GENIE_ID")
    private Long childGenieId;
    
    @Column(name = "HIERARCHY_LEVEL")
    private Integer hierarchyLevel;
}