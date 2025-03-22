package org.project.services;

import org.project.data.Subscriber;
import org.project.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean existsByMsisdn(String msisdn) {
        return subscriberRepository.existsByMsisdn(msisdn);
    }
}
