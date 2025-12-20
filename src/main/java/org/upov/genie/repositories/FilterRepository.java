package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpecies;

import java.util.List;

@Repository
public interface FilterRepository extends JpaRepository<GenieSpecies, Long> {

    /**
     * Get all unique authorities with species counts
     * Returns: List<Object[]> where [0]=authorityCode, [1]=authorityName, [2]=count
     */
    @Query("SELECT DISTINCT a.authorityCode, a.authorityName, COUNT(DISTINCT gp.genieSpecies.genieId) " +
           "FROM GenieSpeciesProtection gp " +
           "JOIN gp.authority a " +
           "JOIN gp.genieSpecies gs " +
           "WHERE gs.codeActive = 'Y' " +
           "AND gp.protectionActive = 'Y' " +
           "AND a.authorityCode IS NOT NULL " +
           "AND a.authorityName IS NOT NULL " +
           "GROUP BY a.authorityCode, a.authorityName " +
           "ORDER BY a.authorityName")
    List<Object[]> findAllAuthoritiesWithCount();

    /**
     * Get all unique families with species counts
     * Returns: List<Object[]> where [0]=familyName, [1]=count
     */
    @Query("SELECT DISTINCT f.familyName, COUNT(DISTINCT gf.genieId) " +
           "FROM GenieFamily gf " +
           "JOIN gf.family f " +
           "JOIN GenieSpecies gs ON gs.genieId = gf.genieId " +
           "WHERE gs.codeActive = 'Y' " +
           "AND f.familyName IS NOT NULL " +
           "GROUP BY f.familyName " +
           "ORDER BY f.familyName")
    List<Object[]> findAllFamiliesWithCount();

    /**
     * Get all unique TWP codes (for crop types) with species counts
     * Returns: List<Object[]> where [0]=twpCode, [1]=twpName, [2]=count
     */
    @Query("SELECT DISTINCT t.twpCode, t.twpName, COUNT(DISTINCT gt.genieId) " +
           "FROM GenieTWP gt " +
           "JOIN gt.twp t " +
           "JOIN GenieSpecies gs ON gs.genieId = gt.genieId " +
           "WHERE gs.codeActive = 'Y' " +
           "AND t.twpCode IS NOT NULL " +
           "GROUP BY t.twpCode, t.twpName " +
           "ORDER BY t.twpCode")
    List<Object[]> findAllTWPsWithCount();
}