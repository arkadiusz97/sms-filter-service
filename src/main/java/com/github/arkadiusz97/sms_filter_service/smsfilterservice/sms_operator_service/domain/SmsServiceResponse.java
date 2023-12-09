package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain;

public class SmsServiceResponse {

    public SmsServiceResponse() {}

    public SmsServiceResponse(SmsServiceStatus smsServiceStatus) {
        this.smsServiceStatus = smsServiceStatus;
    }
    public SmsServiceStatus smsServiceStatus;
}
