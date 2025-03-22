import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.data.CallDataRecord;
import org.project.data.UsageDataReport;
import org.project.repository.CallDataRecordRepository;
import org.project.repository.SubscriberRepository;
import org.project.services.UsageDataReportService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsageDataReportServiceTest {

    @Mock
    private CallDataRecordRepository cdrRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private UsageDataReportService service;

    @Test
    @DisplayName("Получение UDR для абонента без звонков")
    void getUDRByMsisdn_shouldHandleNoCalls() {
        when(cdrRepository.findByCallerNumber(anyString()))
                .thenReturn(Collections.emptyList());

        UsageDataReport report = service.getUDRByMsisdn("79991112233", "2025", "03");

        assertAll(
                () -> assertEquals("00:00:00", report.getIncomingCall().getTotalTime()),
                () -> assertEquals("00:00:00", report.getOutgoingCall().getTotalTime())
        );
    }

    @Test
    @DisplayName("Корректный расчет длительности звонков")
    void calculateDurations_shouldSumCorrectly() {
        List<CallDataRecord> records = List.of(
                createCDR("01", "79991112233", "79876543210", 5),
                createCDR("02", "79876543210", "79991112233", 3)
        );

        when(cdrRepository.findByCallerNumber(anyString()))
                .thenReturn(records);

        UsageDataReport report = service.getUDRByMsisdn("79991112233", "2025", "03");

        assertAll(
                () -> assertEquals("00:05:00", report.getOutgoingCall().getTotalTime()),
                () -> assertEquals("00:03:00", report.getIncomingCall().getTotalTime())
        );
    }

    private CallDataRecord createCDR(String type, String caller, String receiver, int minutes) {
        LocalDateTime start = LocalDateTime.now();
        CallDataRecord cdr = new CallDataRecord(type, caller, receiver, start, start.plusMinutes(minutes));
        cdrRepository.save(cdr);
        return cdr;
    }
}
