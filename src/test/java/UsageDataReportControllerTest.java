import org.junit.jupiter.api.Test;
import org.project.ApplicationStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ApplicationStarter.class)
@AutoConfigureMockMvc
public class UsageDataReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUdr_shouldReturnCorrectData() throws Exception {
        mockMvc.perform(get("/api/udr/by-msisdn")
                        .param("msisdn", "79996667755"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msisdn").value("79996667755"))
                .andExpect(jsonPath("$.outgoingCall.totalTime").exists())
                .andExpect(jsonPath("$.incomingCall.totalTime").exists());
    }
}