package iba.proton.therapy.sensors.dao;

import iba.proton.therapy.sensors.model.keyword.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {

    @Query("SELECT s.keywords FROM SignalEntity s WHERE s.nodeId = :nodeId")
    List<KeywordEntity> findKeywordsByNodeId(@Param("nodeId") String nodeId);
}
