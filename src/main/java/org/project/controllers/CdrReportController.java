package org.project.controllers;

import lombok.RequiredArgsConstructor;
import org.project.services.CallDataRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер, отвечающий за обработку запросов для взаимодействия с отчётами CDR.
 */
@RestController
@RequestMapping("/api/cdr-reports")
@RequiredArgsConstructor
public class CdrReportController {
    private final CallDataRecordService cdrService;

    /**
     * Возвращение информации о создании отчёта в .csv формате для разговоров абонента, попавших в промежуток времени.
     *
     * @param msisdn номер абонента.
     * @param start минимальная граница для времени начала разговора.
     * @param end максимальная граница для времени конца разговора.
     *
     * @return статус, UUID и описание в json формате - при успехе; описание ошибки и UUID - при неудаче.
     */
    @PostMapping
    public ResponseEntity<?> generateReport(
            @RequestParam String msisdn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        try {
            UUID reportId = cdrService.generateCdrReport(msisdn, start, end);
            return ResponseEntity.accepted().body(
                    Map.of(
                            "status", "IN_PROGRESS",
                            "requestId", reportId,
                            "message", "Report generation started"
                    )
            );
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "error", e.getMessage() != null ? e.getMessage() : "Error when creating the report",
                            "requestId", UUID.randomUUID()
                    )
            );
        }
    }

    /**
     * Возвращение статуса отчёта по UUID.
     *
     * @param requestId UUID отчёта.
     *
     * @return статус и UUID в json формате.
     */
    @GetMapping("/{requestId}/status")
    public ResponseEntity<?> checkStatus(@PathVariable UUID requestId) {
        return ResponseEntity.ok(Map.of(
                "status", "COMPLETED",
                "requestId", requestId
        ));
    }
}
