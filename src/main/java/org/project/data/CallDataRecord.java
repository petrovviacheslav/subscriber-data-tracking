package org.project.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CallDataRecords")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CallDataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String callType; // 01 - исходящие, 02 - входящие
    @NonNull
    private String callerNumber;
    @NonNull
    private String receiverNumber;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private LocalDateTime endTime;
    // Геттеры и сеттеры
}
