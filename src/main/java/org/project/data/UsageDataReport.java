package org.project.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsageDataReport {
    private String msisdn;
    private IncomingCall incomingCall;
    private OutgoingCall outgoingCall;

    // Геттеры и сеттеры

    @Setter
    @Getter
    public static class IncomingCall {
        private String totalTime;
        // Геттеры и сеттеры
    }

    @Setter
    @Getter
    public static class OutgoingCall {
        private String totalTime;
        // Геттеры и сеттеры
    }
}

