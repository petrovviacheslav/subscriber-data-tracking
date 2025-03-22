import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.project.ApplicationStarter;
import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = ApplicationStarter.class)
class CallDataRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CallDataRecordRepository repository;

    @Test
    void findByMsisdnAndPeriod_shouldReturnFilteredResults() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 31, 23, 59);

        CallDataRecord invalidRecord = new CallDataRecord(
                "01", "79991112233", "79876543210",
                end.plusDays(1), end.plusDays(2)
        );

        CallDataRecord validRecord = new CallDataRecord(
                "02", "79876543210", "79991112233",
                start.plusDays(1), end.minusDays(1)
        );

        entityManager.persist(validRecord);
        entityManager.persist(invalidRecord);

        List<CallDataRecord> results = repository.findByMsisdnAndPeriod(
                "79876543210", start, end
        );

        assertEquals(1, results.size());
        assertEquals(validRecord.getCallType(), results.get(0).getCallType());
    }
}
