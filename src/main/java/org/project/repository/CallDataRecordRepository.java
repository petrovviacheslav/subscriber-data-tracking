package org.project.repository;

import org.project.data.CallDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CallDataRecordRepository extends JpaRepository<CallDataRecord, Long> {
    List<CallDataRecord> findByCallerNumber(String callerNumber);
    List<CallDataRecord> findByReceiverNumber(String receiveNumber);

    @Query("SELECT c FROM CallDataRecord c WHERE " +
            "(c.callerNumber = :msisdn OR c.receiverNumber = :msisdn) " +
            "AND c.startTime >= :start AND c.endTime <= :end")
    List<CallDataRecord> findByMsisdnAndPeriod(
            @Param("msisdn") String msisdn,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}

