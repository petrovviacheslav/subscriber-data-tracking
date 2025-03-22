import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.data.Subscriber;
import org.project.repository.CallDataRecordRepository;
import org.project.repository.SubscriberRepository;
import org.project.services.CallDataRecordService;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallDataRecordServiceTest {

    @Mock
    private CallDataRecordRepository cdrRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private CallDataRecordService service;

    private List<String> testNumbers;

    @BeforeEach
    void setUp() {
        testNumbers = List.of("79991112233", "79876543210");
        when(subscriberRepository.findAll()).thenReturn(
                testNumbers.stream()
                        .map(number -> {
                            Subscriber s = new Subscriber();
                            s.setMsisdn(number);
                            return s;
                        })
                        .toList()
        );
    }

    @Test
    @DisplayName("Генерация CDR записей создает минимум одну запись")
    void generateCDRRecords_shouldCreateAtLeastOneRecord() {
        service.generateCDRRecords();
        verify(cdrRepository, atLeastOnce()).save(any());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    @DisplayName("Генерация записей с разным количеством дней")
    void generateCallRecord_shouldHandleDifferentDays(int day) {
        assertDoesNotThrow(() ->
                service.generateCallRecord(2, day, 12)
        );
    }

    @Test
    @DisplayName("Генерация записи с одинаковыми номерами абонентов")
    void generateCallRecord_shouldHandleSameNumbers() {
        when(subscriberRepository.findAll()).thenReturn(
                List.of(new Subscriber("79991112233"))
        );

        assertThrows(IllegalStateException.class, () ->
                service.generateCallRecord(1, 1, 12)
        );
    }
}
