package org.project.initializers;

import jakarta.annotation.PostConstruct;
import org.project.data.Subscriber;
import org.project.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SubscriberInitializer {
    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberInitializer(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @PostConstruct
    public void initSubscribers() {
        List<String> numbers = Arrays.asList("79996667755", "79876543221", "79992221122", "79123456789", "79995558888", "79874443333", "79997776666", "79111112222", "79993334444", "79888885555");
        for (String number : numbers) {
            Subscriber subscriber = new Subscriber();
            subscriber.setMsisdn(number);
            subscriberRepository.save(subscriber);
        }
    }
}

