package org.project.services;

import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.data.CallDataRecord;
import org.project.data.Subscriber;
import org.project.repository.CallDataRecordRepository;
import org.project.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class CallDataRecordService {
    private final CallDataRecordRepository cdrRepository;
    private final SubscriberRepository subscriberRepository;
    private final Random random = new Random();
    //    private final List<String> subscribers = Arrays.asList("79996667755", "79876543221", "79992221122", "79123456789", "79995558888", "79874443333", "79997776666", "79111112222", "79993334444", "79888885555");;

    public CallDataRecordService(CallDataRecordRepository cdrRepository, SubscriberRepository subscriberRepository) {
        this.cdrRepository = cdrRepository;
        this.subscriberRepository = subscriberRepository;
    }


    public void generateCDRRecords() {
        for (int year = 0; year < 1; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 28; day++) {
                    for (int hour = 0; hour < 24; hour++) {
                        if (random.nextBoolean()) {
                            generateCallRecord(month, day, hour);
                        }
                    }
                }
            }
        }
    }

    public void generateCallRecord(int month, int day, int hour) {
        Subscriber caller = getRandomSubscriber();
        Subscriber receiver = getRandomSubscriber();
        while (caller.equals(receiver)) {
            receiver = getRandomSubscriber();
        }
        String callType = random.nextBoolean() ? "01" : "02";
        LocalDateTime startTime = LocalDateTime.of(2025, month, day, hour, random.nextInt(60), random.nextInt(60));
        LocalDateTime endTime = startTime.plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));

        CallDataRecord cdr = new CallDataRecord();
        cdr.setCallType(callType);
        cdr.setCallerNumber(caller.getMsisdn());
        cdr.setReceiverNumber(receiver.getMsisdn());
        cdr.setStartTime(startTime);
        cdr.setEndTime(endTime);
        cdrRepository.save(cdr);
    }

    private Subscriber getRandomSubscriber() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscribers.get(random.nextInt(subscribers.size()));
    }

    public UUID generateCdrReport(String msisdn, LocalDateTime start, LocalDateTime end)
            throws IOException {

        validateRequest(msisdn, start, end);

        List<CallDataRecord> records = cdrRepository.findByMsisdnAndPeriod(
                msisdn, start, end
        );

        UUID reportId = UUID.randomUUID();
        Path reportPath = prepareReportDirectory()
                .resolve(String.format("%s_%s.csv", msisdn, reportId));

        try (CSVWriter writer = new CSVWriter(new FileWriter(reportPath.toFile()))) {
            writer.writeNext(new String[]{
                    "Call Type", "Caller", "Receiver", "Start Time", "End Time"
            });

            records.forEach(cdr -> writer.writeNext(new String[]{
                    cdr.getCallType(),
                    cdr.getCallerNumber(),
                    cdr.getReceiverNumber(),
                    cdr.getStartTime().toString(),
                    cdr.getEndTime().toString()
            }));
        }

        return reportId;
    }

    private void validateRequest(String msisdn, LocalDateTime start, LocalDateTime end) {
        if (!subscriberRepository.existsByMsisdn(msisdn)) {
            throw new IllegalArgumentException("Subscriber not found");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }

    private Path prepareReportDirectory() throws IOException {
        Path dir = Paths.get("reports");
        if (!Files.exists(dir)) {
            return Files.createDirectories(dir);
        }
        return dir;
    }

}

