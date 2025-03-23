package org.project.services;

import org.project.data.CallDataRecord;
import org.project.repositories.CallDataRecordRepository;
import org.project.data.Subscriber;
import org.project.data.UsageDataReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с UDR.
 * <p>
 * Этот класс предоставляет методы для создания UDR записей.
 */
@Service
public class UsageDataReportService {
    private final CallDataRecordRepository cdrRepository;
    private final SubscriberService subscriberService;

    @Autowired
    public UsageDataReportService(CallDataRecordRepository cdrRepository, SubscriberService subscriberService) {
        this.cdrRepository = cdrRepository;
        this.subscriberService = subscriberService;
    }

    /**
     * Создание UDR по номеру телефона и заданному месяцу (опционально).
     *
     * @param msisdn номер телефона абонента.
     * @param year   год.
     * @param month  месяц.
     * @return UsageDataReport.
     */
    public UsageDataReport getUDRByMsisdn(String msisdn, String year, String month) throws IllegalArgumentException {
        List<CallDataRecord> cdrs;

        if (!subscriberService.existsByMsisdn(msisdn)) {
            throw new IllegalArgumentException("Subscriber not found");
        }

        if (year == null || month == null) {
            cdrs = cdrRepository.findByCallerNumberOrReceiverNumber(msisdn);
        } else {
            validateMonthAndYear(month, year);

            int year_int = Integer.parseInt(year);
            int month_int = Integer.parseInt(month);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime start;
            if (month_int < 10)
                start = LocalDateTime.parse(String.format("%d-0%d-01T00:00:00", year_int, month_int), formatter);
            else start = LocalDateTime.parse(String.format("%d-%d-01T00:00:00", year_int, month_int), formatter);

            if (month_int == 11) {
                month_int = 1;
                year_int++;
            } else month_int++;

            LocalDateTime end;
            if (month_int < 10)
                end = LocalDateTime.parse(String.format("%d-0%d-01T00:00:00", year_int, month_int), formatter);
            else end = LocalDateTime.parse(String.format("%d-%d-01T00:00:00", year_int, month_int), formatter);

            cdrs = cdrRepository.findByMsisdnAndPeriod(msisdn, start, end);
        }

        UsageDataReport udr = new UsageDataReport();
        udr.setMsisdn(msisdn);
        udr.setIncomingCall(new UsageDataReport.IncomingCall());
        udr.setOutgoingCall(new UsageDataReport.OutgoingCall());

        long incomingDuration = 0;
        long outgoingDuration = 0;

        for (CallDataRecord cdr : cdrs) {
            if (cdr.getCallerNumber().equals(msisdn)) {
                outgoingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
            } else {
                incomingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
            }

        }

        udr.getIncomingCall().setTotalTime(formatDuration(incomingDuration));
        udr.getOutgoingCall().setTotalTime(formatDuration(outgoingDuration));

        return udr;
    }

    private void validateMonthAndYear(String month, String year) throws IllegalArgumentException {
        int year_int = Integer.parseInt(year);
        int month_int = Integer.parseInt(month);
        if (year_int < 1900 || month_int > 12 || month_int < 1) {
            throw new IllegalArgumentException("Invalid parameters (year or month)");
        }
    }

    /**
     * UDR всех пользователей по заданному месяцу.
     *
     * @param year  год.
     * @param month месяц.
     * @return список UsageDataReport.
     */
    public List<UsageDataReport> getUDRsForAllSubscribers(String year, String month) throws IllegalArgumentException {
        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        List<UsageDataReport> udRs = new ArrayList<>();

        for (Subscriber subscriber : subscribers) {
            UsageDataReport udr = getUDRByMsisdn(subscriber.getMsisdn(), year, month);
            udRs.add(udr);
        }

        return udRs;
    }

    /**
     * Форматирование даты.
     *
     * @param seconds секунды.
     * @return строка, отформатированная в соответствии с шаблоном.
     */
    public String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

