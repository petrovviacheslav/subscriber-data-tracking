import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CallDataRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private CallDataRecordRepository repository;

    @Test
    @DisplayName("Поиск записей по номеру и периоду")
    void findByMsisdnAndPeriod_shouldReturnFilteredResults() {
        LocalDateTime start = LocalDateTime.parse("2025-03-01T00:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-03-31T23:59:59");

        CallDataRecord valid = createCDR(start.plusDays(1), end.minusDays(1));
        CallDataRecord invalid = createCDR(end.plusDays(1), end.plusDays(2));

        entityManager.persist(valid);
        entityManager.persist(invalid);

        List<CallDataRecord> results = repository.findByMsisdnAndPeriod(
                "79991112233", start, end
        );

        assertEquals(1, results.size());
    }

    private CallDataRecord createCDR(LocalDateTime start, LocalDateTime end) {
        return new CallDataRecord(
                "01",
                "79991112233",
                "79876543210",
                start,
                end
        );
    }
}
