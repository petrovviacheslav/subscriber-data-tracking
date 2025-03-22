package org.project.initializers;

import jakarta.annotation.PostConstruct;
import org.project.data.CallDataRecord;
import org.project.data.Subscriber;
import org.project.repository.CallDataRecordRepository;
import org.project.repository.SubscriberRepository;
import org.project.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Класс инициализации первых десяти абонентов.
 */
@Component
public class DataInitializer {
    private final SubscriberRepository subscriberRepository;
    private final CallDataRecordRepository cdrRepository;
    private final Random random = new Random();
    private final List<String> numbers = Arrays.asList("79996667755", "79876543221", "79992221122", "79123456789", "79995558888", "79874443333", "79997776666", "79111112222", "79993334444", "79888885555");

    @Autowired
    public DataInitializer(SubscriberRepository subscriberRepository, CallDataRecordRepository cdrRepository) {
        this.subscriberRepository = subscriberRepository;
        this.cdrRepository = cdrRepository;
    }

    /**
     * Инициализация первых абонентов и запись из в бд.
     */
    @PostConstruct
    public void initSubscribers() {
        for (String number : numbers) {
            Subscriber subscriber = new Subscriber();
            subscriber.setMsisdn(number);
            subscriberRepository.save(subscriber);
        }
        generateCDRRecords(numbers);
    }


    /**
     * Генерирование записей за 1 год.
     */
    public void generateCDRRecords(List<String> numbers) {
        for (int year = 0; year < 1; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 28; day++) {
                    for (int hour = 0; hour < 24; hour++) {
                        if (random.nextBoolean()) {
                            String caller = getRandomSubscriber(numbers);
                            String receiver = getRandomSubscriber(numbers);
                            while (caller.equals(receiver)) {
                                receiver = getRandomSubscriber(numbers);
                            }
                            generateCallRecord(caller, receiver, month, day, hour);
                        }
                    }
                }
            }
        }
    }


    /**
     * Создание CDR со случайным временем разговора в нужный час дня.
     *
     * @param month  месяц разговора.
     * @param day день (месяца) разговора.
     * @param hour час разговора.
     */
    public void generateCallRecord(String caller, String receiver, int month, int day, int hour) {

        String callType = random.nextBoolean() ? "01" : "02";
        LocalDateTime startTime = LocalDateTime.of(2025, month, day, hour, random.nextInt(60), random.nextInt(60));
        LocalDateTime endTime = startTime.plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));

        CallDataRecord cdr = new CallDataRecord();
        cdr.setCallType(callType);
        cdr.setCallerNumber(caller);
        cdr.setReceiverNumber(receiver);
        cdr.setStartTime(startTime);
        cdr.setEndTime(endTime);
        cdrRepository.save(cdr);
    }

    /**
     * Возвращение случайного абонента.
     *
     * @return абонент.
     */
    private String getRandomSubscriber(List<String> subscriberList) {
        return subscriberList.get(random.nextInt(subscriberList.size()));
    }
}

