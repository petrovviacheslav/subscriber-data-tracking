package org.project.services;

import org.project.data.CallDataRecord;
import org.project.repository.CallDataRecordRepository;
import org.project.data.Subscriber;
import org.project.data.UsageDataReport;
import org.project.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsageDataReportService {
    private final CallDataRecordRepository cdrRepository;
    private final SubscriberRepository subscriberRepository;

    @Autowired
    public UsageDataReportService(CallDataRecordRepository cdrRepository, SubscriberRepository subscriberRepository) {
        this.cdrRepository = cdrRepository;
        this.subscriberRepository = subscriberRepository;
    }

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
            outgoingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
        }

        for (CallDataRecord cdr : cdr_by_receiver) {
            incomingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).toSeconds();
        }

        udr.getIncomingCall().setTotalTime(formatDuration(incomingDuration));
        udr.getOutgoingCall().setTotalTime(formatDuration(outgoingDuration));

        return udr;
    }

    public List<UsageDataReport> getUDRsForAllSubscribers(String year, String month) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<UsageDataReport> udRs = new ArrayList<>();

        for (Subscriber subscriber : subscribers) {
            UsageDataReport udr = getUDRByMsisdn(subscriber.getMsisdn(), year, month);
            udRs.add(udr);
        }

        return udRs;
    }

    public String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

