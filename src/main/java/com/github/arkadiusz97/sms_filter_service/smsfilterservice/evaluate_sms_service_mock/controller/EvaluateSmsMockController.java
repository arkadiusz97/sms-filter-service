package com.github.arkadiusz97.sms_filter_service.smsfilterservice.evaluate_sms_service_mock.controller;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberRequest;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberResponse;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.evaluate_sms_service_mock.service.EvaluateSmsMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluateSmsMockController {

    @Autowired
    EvaluateSmsMockService evaluateSmsMockService;

    @PostMapping("evaluateSmsMock/evaluateSms")
    public PhoneNumberResponse evaluateSms(@RequestBody PhoneNumberRequest phoneNumberRequest) {
        return evaluateSmsMockService.evaluateSms(phoneNumberRequest);
    }

}
