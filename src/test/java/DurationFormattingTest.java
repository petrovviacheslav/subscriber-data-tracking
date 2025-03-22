import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.project.services.CallDataRecordService;
import org.project.services.UsageDataReportService;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFormattingTest {
    @MockBean
    private UsageDataReportService udrService;

    @ParameterizedTest
    @CsvSource({
            "3661, 01:01:01",
            "0, 00:00:00",
            "3599, 00:59:59",
            "86400, 24:00:00"
    })
    @DisplayName("Форматирование длительности в ЧЧ:ММ:СС")
    void formatDuration_shouldHandleVariousCases(long seconds, String expected) {
        assertEquals(expected, udrService.formatDuration(seconds));
    }
}
