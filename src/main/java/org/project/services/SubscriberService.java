package org.project.services;

import org.project.data.Subscriber;
import org.project.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс для работы с абонентами.
 * <p>
 * Этот класс предоставляет методы для получения списка абонентов и проверки на существование абонента.
 */
@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Получение списка всех абонентов.
     *
     * @return список абонентов.
     */
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    /**
     * Проверка на существование такого абонента по номеру телефона.
     *
     * @return true - если есть в базе данных, иначе false.
     */
    public boolean existsByMsisdn(String msisdn) {
        return subscriberRepository.existsByMsisdn(msisdn);
    }
}
