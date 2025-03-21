package org.project.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Subcribers")
@Setter
@Getter
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String msisdn; // Номер мобильного абонента
    // Геттеры и сеттеры
}

