import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.initializers.DataInitializer;
import org.project.repositories.CallDataRecordRepository;
import org.project.services.CallDataRecordService;
import org.project.services.SubscriberService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CallDataRecordServiceTest {
    @Mock
    private SubscriberService subscriberService;
    @InjectMocks
    private CallDataRecordService service;
    @InjectMocks
    private DataInitializer dataInitializer;
    @Mock
    private CallDataRecordRepository cdrRepository;

    List<String> testNumbers = List.of("79991112233", "79876543210");

    @Test
    @DisplayName("Генерация CDR отчётов для 2 пользователей")
    void generateCDRReport_shouldCreateReport() {

        when(subscriberService.existsByMsisdn(testNumbers.get(0))).thenReturn(true);
        when(subscriberService.existsByMsisdn(testNumbers.get(1))).thenReturn(true);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime1 = LocalDateTime.parse("2025-01-01T00:00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse("2025-01-21T00:00:00", formatter);
        dataInitializer.generateCDRRecords(testNumbers);

        assertDoesNotThrow(() ->
                service.generateCdrReport(testNumbers.get(0), dateTime1, dateTime2));
        assertDoesNotThrow(() ->
                service.generateCdrReport(testNumbers.get(1), dateTime1, dateTime2));
    }
}
