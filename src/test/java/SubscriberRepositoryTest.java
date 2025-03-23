import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.ApplicationStarter;
import org.project.data.Subscriber;
import org.project.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = ApplicationStarter.class)
class SubscriberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubscriberRepository repository;

    @Test
    @DisplayName("Проверка наличия в базе данных абонента")
    void checkByMsisdn_shouldReturnExistingResults() {
        Subscriber newSub = new Subscriber("79991112233");

        entityManager.persist(newSub);

        assertFalse(repository.existsByMsisdn("79876543210"));
        assertTrue(repository.existsByMsisdn("79991112233"));
    }
}
