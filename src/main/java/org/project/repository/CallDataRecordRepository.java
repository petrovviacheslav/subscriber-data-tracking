package org.project.repository;

import org.project.data.CallDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallDataRecordRepository extends JpaRepository<CallDataRecord, Long> {
//    CallDataRecord findById(String callId);
    List<CallDataRecord> findByCallerNumber(String callerNumber);
    List<CallDataRecord> findByReceiverNumber(String receiveNumber);
}

