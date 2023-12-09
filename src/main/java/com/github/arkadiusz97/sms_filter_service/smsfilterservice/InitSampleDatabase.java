package com.github.arkadiusz97.sms_filter_service.smsfilterservice;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.Client;
import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.repository.ClientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitSampleDatabase {

    @Autowired
    ClientRepository clientRepository;

    @PostConstruct
    public void init() {
        Client client1 = new Client();
        client1.number = "111";
        client1.isUsingService = true;
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.number = "222";
        client2.isUsingService = false;
        clientRepository.save(client2);

    }
}
