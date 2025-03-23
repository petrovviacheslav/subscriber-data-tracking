package org.project.services;

import com.opencsv.CSVWriter;

import org.project.data.CallDataRecord;
import org.project.repositories.CallDataRecordRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Класс для работы с CDR.
 * <p>
 * Этот класс предоставляет методы для создания CDR и отчётов в формате .csv с CDR
 */
@Service
public class CallDataRecordService {
    private final CallDataRecordRepository cdrRepository;
    private final SubscriberService subscriberService;

    public CallDataRecordService(CallDataRecordRepository cdrRepository, SubscriberService subscriberService) {
        this.cdrRepository = cdrRepository;
        this.subscriberService = subscriberService;
    }

    /**
     * Создание отчета с CDR.
     *
     * @param msisdn номер абонента.
     * @param start минимальная граница для времени начала разговора.
     * @param end максимальная граница для времени конца разговора.
     *
     * @return уникальный UUID.
     */
    public UUID generateCdrReport(String msisdn, LocalDateTime start, LocalDateTime end)
            throws IllegalArgumentException, IOException {

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

    /**
     * Проверка номера телефона и времени разговора.
     *
     * @param msisdn  номер телефона абонента.
     * @param start время начала разговора.
     * @param end время окончания разговора.
     *
     * @throws IllegalArgumentException Если абонента нет в базе данных или разговор длился отрицательное время.
     */
    private void validateRequest(String msisdn, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        if (!subscriberService.existsByMsisdn(msisdn)) {
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

