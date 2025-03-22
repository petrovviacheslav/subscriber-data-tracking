import org.junit.jupiter.api.Test;
import org.project.services.CallDataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CdrReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallDataRecordService reportService;

    @Test
    void generateReport_shouldReturnAcceptedStatus() throws Exception {
        // Arrange
        UUID testUuid = UUID.randomUUID();
        when(reportService.generateCdrReport(anyString(), any(), any()))
                .thenReturn(testUuid);

        // Act & Assert
        mockMvc.perform(post("/api/cdr-reports")
                        .param("msisdn", "79991112233")
                        .param("start", "2025-01-01T00:00:00")
                        .param("end", "2025-01-15T23:59:59"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.requestId").value(testUuid.toString()));
    }

    @Test
    void generateReport_shouldHandleInvalidDateRange() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/cdr-reports")
                        .param("msisdn", "79991112233")
                        .param("start", "2025-01-20T00:00:00")
                        .param("end", "2025-01-01T23:59:59"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
