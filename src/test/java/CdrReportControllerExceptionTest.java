import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.controllers.CdrReportController;
import org.project.services.CallDataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CdrReportController.class)
class CdrReportControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallDataRecordService reportService;

    @Test
    @DisplayName("Обработка неверного формата даты")
    void handleInvalidDateFormat() throws Exception {
        mockMvc.perform(post("/api/cdr-reports")
                        .param("msisdn", "79991112233")
                        .param("start", "invalid-date")
                        .param("end", "2025-01-01T00:00:00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Обработка несуществующего абонента")
    void handleNonExistentSubscriber() throws Exception {
        when(reportService.generateCdrReport(anyString(), any(), any()))
                .thenThrow(new IllegalArgumentException("Subscriber not found"));

        mockMvc.perform(post("/api/v1/cdr-reports")
                        .param("msisdn", "79990000000")
                        .param("start", "2025-01-01T00:00:00")
                        .param("end", "2025-01-15T23:59:59"))
                .andExpect(jsonPath("$.error").exists());
    }
}
