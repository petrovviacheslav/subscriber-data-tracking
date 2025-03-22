package org.project.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс UDR - Usage Data Report - Отчет об использовании данных.
 */
@Setter
@Getter
public class UsageDataReport {
    private String msisdn;
    private IncomingCall incomingCall;
    private OutgoingCall outgoingCall;

    @Setter
    @Getter
    public static class IncomingCall {
        private String totalTime;
    }

    @Setter
    @Getter
    public static class OutgoingCall {
        private String totalTime;
    }
}

