package org.project.repositories;

import org.project.data.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс с методами для взаимодействия с таблицей Subscriber из базы данных.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsByMsisdn(String msisdn);
}

