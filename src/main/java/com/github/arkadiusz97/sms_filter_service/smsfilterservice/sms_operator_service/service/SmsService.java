package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.service;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.ConfidenceLevel;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberRequest;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.domain.PhoneNumberResponse;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.Client;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.Sms;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.SmsServiceResponse;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.SmsServiceStatus;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.repository.ClientRepository;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileInputStream;
import java.util.Properties;

@Service
public class SmsService {

    private OkHttpClient.Builder httpClient;
    private Retrofit retrofit;
    private EvaluateSmsService evaluateSmsService;
    private String operatorSpecialNumber = "special-number";
    public SmsService() {
        httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();
        evaluateSmsService = retrofit.create(EvaluateSmsService.class);

        String rootPath = System.getProperty("user.dir");
        String appConfigPath = rootPath + "\\app.properties";
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        operatorSpecialNumber = appProps.getProperty("operatorSpecialNumber");
    }

    @Autowired
    ClientRepository clientRepository;
    public SmsServiceResponse handleSms(Sms sms) {
        Client client = clientRepository.findOneByNumber(sms.recipient);
        if(sms.recipient.equals(operatorSpecialNumber)) {
            return handleUserStartOrStopSms(sms);
        }
        if(client == null) {
            SmsServiceResponse smsServiceResponse = new SmsServiceResponse();
            smsServiceResponse.smsServiceStatus = SmsServiceStatus.USER_NOT_FOUND;
            return smsServiceResponse;
        }
        return handleOtherSms(sms, client);
    }

    private SmsServiceResponse handleUserStartOrStopSms(Sms sms) {
        SmsServiceResponse response = new SmsServiceResponse();
        Client client = clientRepository.findOneByNumber(sms.sender);
        if(client == null) {
            response.smsServiceStatus = SmsServiceStatus.USER_NOT_FOUND;
            return response;
        }
        if(sms.message.equals("START")) {
            client.isUsingService = true;
            clientRepository.save(client);
            response.smsServiceStatus = SmsServiceStatus.USER_START;
        } else if(sms.message.equals("STOP")) {
            client.isUsingService = false;
            clientRepository.save(client);
            response.smsServiceStatus = SmsServiceStatus.USER_STOP;
        } else {
            response.smsServiceStatus = SmsServiceStatus.NOTHING_CHANGED;
        }
        return response;
    }
    private SmsServiceResponse handleOtherSms(Sms sms, Client client) {
        PhoneNumberRequest request = new PhoneNumberRequest();
        SmsServiceResponse result = new SmsServiceResponse();
        if(client.isUsingService) {
            request.phoneNumber = sms.sender;
            Call<PhoneNumberResponse> callSync = evaluateSmsService.getSmsEvaluation(request);
            try {
                Response<PhoneNumberResponse> response = callSync.execute();
                PhoneNumberResponse phoneNumberResponse = response.body();
                result.smsServiceStatus = smsServiceStatusFromPhoneNumberResponse(phoneNumberResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result.smsServiceStatus = SmsServiceStatus.SERVICE_NOT_USED_BY_USER;
        }
        return result;
    }

    private SmsServiceStatus smsServiceStatusFromPhoneNumberResponse(PhoneNumberResponse phoneNumberResponse) {
        if(phoneNumberResponse.confidenceLevel == ConfidenceLevel.MEDIUM ||
            phoneNumberResponse.confidenceLevel == ConfidenceLevel.HIGH) {

            return SmsServiceStatus.NOT_SAFE;
        } else {
            return SmsServiceStatus.SAFE;
        }
    }

}
