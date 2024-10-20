package iba.proton.therapy.sensors.dao;

import iba.proton.therapy.sensors.model.signal.DeadbandType;
import iba.proton.therapy.sensors.model.signal.SignalEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SignalRepository extends JpaRepository<SignalEntity, String> {

    @Query("""
            SELECT s FROM SignalEntity s
            JOIN s.keywords k
               WHERE (:isActive IS NULL OR s.isActive = :isActive)
               AND (:deadbandType IS NULL OR s.deadbandType = :deadbandType)
               AND (:samplingInterval IS NULL OR s.samplingInterval = :samplingInterval)
               AND (:deadbandValue IS NULL OR s.deadbandValue = :deadbandValue)
               AND (:keywordIds IS NULL OR k.id IN :keywordIds)
               GROUP BY s.nodeId
               HAVING (COUNT(DISTINCT k.id) >= :count)
            """)
    List<SignalEntity> findByCriteria(Pageable pageable, @Param("isActive") Boolean isActive,
                                      @Param("deadbandType") DeadbandType deadbandType,
                                      @Param("deadbandValue") Integer deadbandValue,
                                      @Param("samplingInterval") Integer samplingInterval,
                                      @Param("keywordIds") Set<Integer> keywordIds,
                                      @Param("count") Integer count);
}
