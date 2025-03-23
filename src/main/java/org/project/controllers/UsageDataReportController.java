package org.project.controllers;

import org.project.data.UsageDataReport;
import org.project.services.UsageDataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер, отвечающий за обработку запросов создания и возвращения UDR записей.
 */
@RestController
@RequestMapping("/api/udr")
public class UsageDataReportController {

    private final UsageDataReportService udrService;

    @Autowired
    public UsageDataReportController(UsageDataReportService udrService) {
        this.udrService = udrService;
    }

    /**
     * Возвращение UDR по номеру телефона и заданному месяцу (опционально).
     *
     * @param msisdn номер телефона абонента.
     * @param year   год.
     * @param month  месяц.
     * @return UsageDataReport в json формате.
     */
    @GetMapping("/by-msisdn")
    public ResponseEntity<?> getUDRByMsisdn(@RequestParam String msisdn, @RequestParam(required = false) String year, @RequestParam(required = false) String month) {
        try {
            UsageDataReport udr;
            if (year == null || month == null) {
                udr = udrService.getUDRByMsisdn(msisdn, null, null); // весь период
            } else {
                udr = udrService.getUDRByMsisdn(msisdn, year, month);
            }

            return ResponseEntity.ok().body(udr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message", e.getMessage()
                    ));
        }
    }

    /**
     * Возвращение UDR всех пользователей по заданному месяцу.
     *
     * @param year  год.
     * @param month месяц.
     * @return список UsageDataReport в json формате.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getUDRsForAllSubscribers(@RequestParam String year, @RequestParam String month) {
        try {
            List<UsageDataReport> udrs = udrService.getUDRsForAllSubscribers(year, month);
            return ResponseEntity.ok().body(udrs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "ERROR"
                    ));
        }
    }
}

