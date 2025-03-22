package org.project.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Subcribers")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String msisdn; // Номер мобильного абонента
}

