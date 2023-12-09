package com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain;

public class PhoneNumberResponse {
    public PhoneNumberResponse(MainThreatType mainThreatType, ConfidenceLevel confidenceLevel) {
        this.mainThreatType = mainThreatType;
        this.confidenceLevel = confidenceLevel;
    }
    public MainThreatType mainThreatType;
    public ConfidenceLevel confidenceLevel;

}
