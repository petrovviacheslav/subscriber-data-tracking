package org.project.services;

import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.project.data.Subscriber;
import org.project.data.UsageDataReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
     * @param msisdn  номер телефона абонента.
     * @param year год.
     * @param month месяц.
     *
     * @return UsageDataReport.
     */
    public UsageDataReport getUDRByMsisdn(String msisdn, String year, String month) {
        List<CallDataRecord> cdr_by_caller = cdrRepository.findByCallerNumber(msisdn);
        List<CallDataRecord> cdr_by_receiver = cdrRepository.findByReceiverNumber(msisdn);

        UsageDataReport udr = new UsageDataReport();
        udr.setMsisdn(msisdn);
        udr.setIncomingCall(new UsageDataReport.IncomingCall());
        udr.setOutgoingCall(new UsageDataReport.OutgoingCall());

        long incomingDuration = 0;
        long outgoingDuration = 0;

        for (CallDataRecord cdr : cdr_by_caller) {
            if (cdr.getCallerNumber().equals(msisdn)) {
                outgoingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
            } else {
                incomingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
            }

        }

        for (CallDataRecord cdr : cdr_by_receiver) {
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

    /**
     * UDR всех пользователей по заданному месяцу.
     *
     * @param year год.
     * @param month месяц.
     *
     * @return список UsageDataReport.
     */
    public List<UsageDataReport> getUDRsForAllSubscribers(String year, String month) {
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
     *
     * @return строка, отформатированная в соответствии с шаблоном.
     */
    public String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

