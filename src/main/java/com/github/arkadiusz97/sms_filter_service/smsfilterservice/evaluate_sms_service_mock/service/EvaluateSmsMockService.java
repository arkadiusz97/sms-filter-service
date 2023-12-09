package com.github.arkadiusz97.sms_filter_service.smsfilterservice.evaluate_sms_service_mock.service;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.ConfidenceLevel;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.MainThreatType;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberRequest;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberResponse;
import org.springframework.stereotype.Service;

@Service
public class EvaluateSmsMockService {
    public PhoneNumberResponse evaluateSms(PhoneNumberRequest request) {
        if(request.phoneNumber.equals("some-unsafe-number")) {
            return new PhoneNumberResponse(MainThreatType.MALWARE, ConfidenceLevel.MEDIUM);
        } else {
            return new PhoneNumberResponse(MainThreatType.THREAT_TYPE_UNSPECIFIED, ConfidenceLevel.SAFE);
        }
    }
}
