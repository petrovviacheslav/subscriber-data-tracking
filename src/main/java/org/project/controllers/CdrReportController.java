package org.project.controllers;

import lombok.RequiredArgsConstructor;
import org.project.services.CallDataRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cdr-reports")
@RequiredArgsConstructor
public class CdrReportController {
    private final CallDataRecordService cdrService;

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
                            "error", e.getMessage(),
                            "requestId", UUID.randomUUID()
                    )
            );
        }
    }

    @GetMapping("/{requestId}/status")
    public ResponseEntity<?> checkStatus(@PathVariable UUID requestId) {
        // Реализация проверки статуса (можно хранить в БД или кэше)
        return ResponseEntity.ok(Map.of(
                "status", "COMPLETED",
                "requestId", requestId
        ));
    }
}
