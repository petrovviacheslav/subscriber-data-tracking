import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.data.CallDataRecord;
import org.project.data.UsageDataReport;
import org.project.repository.CallDataRecordRepository;
import org.project.services.UsageDataReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsageDataReportServiceTest {

    @Mock
    private CallDataRecordRepository cdrRepository;

    @InjectMocks
    private UsageDataReportService service;

    @Test
    @DisplayName("Получение UDR для абонента без звонков")
    void getUDRByMsisdn_shouldHandleNoCalls() {
        when(cdrRepository.findByMsisdnAndPeriod(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());

        UsageDataReport report = service.getUDRByMsisdn("79991112233", "2025", "3");

        assertAll(
                () -> assertEquals("00:00:00", report.getIncomingCall().getTotalTime()),
                () -> assertEquals("00:00:00", report.getOutgoingCall().getTotalTime())
        );
    }

    @Test
    @DisplayName("Корректный расчет длительности звонков")
    void calculateDurations_shouldSumCorrectly() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        List<CallDataRecord> records = List.of(
                createCDR("01", "79991112233", "79876543210", LocalDateTime.parse("2025-03-02T00:00:00", formatter), 5),
                createCDR("02", "79876543210", "79991112233", LocalDateTime.parse("2025-03-07T00:00:00", formatter),3)
        );

        when(cdrRepository.findByMsisdnAndPeriod(anyString(), any(), any()))
                .thenReturn(records);

        UsageDataReport report = service.getUDRByMsisdn("79991112233", "2025", "3");

        assertAll(
                () -> assertEquals("00:05:00", report.getOutgoingCall().getTotalTime()),
                () -> assertEquals("00:03:00", report.getIncomingCall().getTotalTime())
        );
    }

    private CallDataRecord createCDR(String type, String caller, String receiver, LocalDateTime start, int minutes) {
        CallDataRecord cdr = new CallDataRecord(type, caller, receiver, start, start.plusMinutes(minutes));
        cdrRepository.save(cdr);
        return cdr;
    }
}
