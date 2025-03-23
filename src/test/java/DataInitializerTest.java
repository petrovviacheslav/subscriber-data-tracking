import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.initializers.DataInitializer;
import org.project.repositories.CallDataRecordRepository;
import org.project.services.SubscriberService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private CallDataRecordRepository cdrRepository;

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private DataInitializer dataInit;

    List<String> testNumbers = List.of("79991112233", "79876543210");


    @Test
    @DisplayName("Генерация CDR записей создает минимум одну запись")
    void generateCDRRecords_shouldCreateAtLeastOneRecord() {
        dataInit.generateCDRRecords(testNumbers);
        verify(cdrRepository, atLeastOnce()).save(any());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    @DisplayName("Генерация записей с разным количеством дней")
    void generateCallRecord_shouldHandleDifferentDays(int day) {

        assertDoesNotThrow(() ->
                dataInit.generateCallRecord(testNumbers.get(0), testNumbers.get(1), 2, day, 12)
        );

        assertDoesNotThrow(() ->
                dataInit.generateCallRecord(testNumbers.get(1), testNumbers.get(0), 3, day, 14)
        );
    }


}
