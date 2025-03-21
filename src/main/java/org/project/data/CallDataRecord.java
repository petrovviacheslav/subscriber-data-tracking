package org.project.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CallDataRecords")
@Getter
@Setter
public class CallDataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String callType; // 01 - исходящие, 02 - входящие
    private String callerNumber;
    private String receiverNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // Геттеры и сеттеры
}
