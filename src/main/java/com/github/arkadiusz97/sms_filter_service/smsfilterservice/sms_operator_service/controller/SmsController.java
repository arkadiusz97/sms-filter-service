package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.controller;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.Sms;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.SmsServiceResponse;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    @PostMapping("smsOperatorService/handleSms")
    SmsServiceResponse handleSms(@RequestBody Sms sms) {
        return smsService.handleSms(sms);
    }
}
