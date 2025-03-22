package org.project.controllers;

import org.project.data.UsageDataReport;
import org.project.services.UsageDataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/udr")
public class UsageDataReportController {

    private final UsageDataReportService udrService;

    @Autowired
    public UsageDataReportController(UsageDataReportService udrService) {
        this.udrService = udrService;
    }

    @GetMapping("/by-msisdn")
    public UsageDataReport getUDRByMsisdn(@RequestParam String msisdn, @RequestParam(required = false) String year, @RequestParam(required = false) String month) {
        if (year == null || month == null) {
            return udrService.getUDRByMsisdn(msisdn, null, null); // весь период
        } else {
            return udrService.getUDRByMsisdn(msisdn, year, month);
        }
    }

    @GetMapping("/all")
    public List<UsageDataReport> getUDRsForAllSubscribers(@RequestParam String year, @RequestParam String month) {
        return udrService.getUDRsForAllSubscribers(year, month);
    }
}

