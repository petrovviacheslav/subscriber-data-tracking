package org.project.initializers;

import jakarta.annotation.PostConstruct;
import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class CallDataRecordInitializer {
    private final CallDataRecordRepository cdrRepository;
    private final Random random = new Random();
    private final List<String> numbers = Arrays.asList("79996667755", "79876543221", "79992221122", "79123456789", "79995558888", "79874443333", "79997776666", "79111112222", "79993334444", "79888885555");


    @Autowired
    public CallDataRecordInitializer(CallDataRecordRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

    @PostConstruct
    public void initCallDataRecords() {
        generateCDRRecords();
    }


    public void generateCDRRecords() {
        for (int year = 0; year < 1; year++) {
            for (int month = 1; month <= 12; month+=2) {
                for (int day = 1; day <= 28; day+=2) {
                    for (int hour = 0; hour < 24; hour+=2) {
                        if (random.nextBoolean()) {
                            generateCallRecord(month, day, hour);
                        }
                    }
                }
            }
        }
    }

    private void generateCallRecord(int month, int day, int hour) {
        String callerNumber = getRandomNumber();
        String receiverNumber = getRandomNumber();
        while (callerNumber.equals(receiverNumber)) {
            receiverNumber = getRandomNumber();
        }
        String callType = random.nextBoolean() ? "01" : "02";
        LocalDateTime startTime = LocalDateTime.of(2025, month, day, hour, random.nextInt(60), random.nextInt(60));
        LocalDateTime endTime = startTime.plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));

        CallDataRecord cdr = new CallDataRecord();
        cdr.setCallType(callType);
        cdr.setCallerNumber(callerNumber);
        cdr.setReceiverNumber(receiverNumber);
        cdr.setStartTime(startTime);
        cdr.setEndTime(endTime);
        cdrRepository.save(cdr);
    }

    private String getRandomNumber() {
        return numbers.get(random.nextInt(numbers.size()));
    }
}
