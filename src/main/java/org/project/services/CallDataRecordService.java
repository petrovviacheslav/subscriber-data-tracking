package org.project.services;

import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CallDataRecordService {
    private final CallDataRecordRepository cdrRepository;

    @Autowired
    public CallDataRecordService(CallDataRecordRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

}

