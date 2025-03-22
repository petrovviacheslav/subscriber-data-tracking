import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.ApplicationStarter;
import org.project.data.CallDataRecord;
import org.project.data.Subscriber;
import org.project.repository.CallDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@ContextConfiguration(classes = ApplicationStarter.class)
class CallDataRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CallDataRecordRepository repository;

    @Test
    void findByMsisdnAndPeriod_shouldReturnFilteredResults() {
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

    @Test
    void findOnlyCaller_shouldReturnCaller() {
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 31, 23, 59);

        CallDataRecord validRecord = new CallDataRecord(
                "01", "79991112233", "79876543210",
                end.minusDays(2), end
        );

        CallDataRecord invalidRecord = new CallDataRecord(
                "01", "79876543210", "79991112233",
                start.plusDays(1), end.minusDays(2)
        );

        entityManager.persist(validRecord);
        entityManager.persist(invalidRecord);

        List<CallDataRecord> results = repository.findByCallerNumber("79991112233");

        assertEquals(1, results.size());
        assertEquals(validRecord.getCallerNumber(), results.get(0).getCallerNumber());
    }

    @Test
    void findOnlyCallerOrReceiver_shouldReturnCDRbyCallerOrReceiver() {
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 31, 23, 59);

        CallDataRecord firstRecord = new CallDataRecord(
                "01", "79991112233", "79876543210",
                end.minusDays(2), end
        );

        CallDataRecord secondRecord = new CallDataRecord(
                "01", "79876543210", "79991112233",
                start.plusDays(1), end.minusDays(2)
        );

        entityManager.persist(firstRecord);
        entityManager.persist(secondRecord);

        List<CallDataRecord> results_caller = repository.findByCallerNumber("79991112233");
        List<CallDataRecord> results_receiver = repository.findByReceiverNumber("79991112233");

        assertEquals(1, results_caller.size());
        assertEquals(firstRecord.getCallerNumber(), results_caller.get(0).getCallerNumber());

        assertEquals(1, results_receiver.size());
        assertEquals(secondRecord.getReceiverNumber(), results_receiver.get(0).getReceiverNumber());
    }
}
