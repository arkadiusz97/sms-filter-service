package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.service;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberRequest;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EvaluateSmsService {
    @POST("evaluateSmsMock/evaluateSms")
    public Call<PhoneNumberResponse> getSmsEvaluation(@Body PhoneNumberRequest phoneNumberRequest);
}
