package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private int id;

    public String number;
    public Boolean isUsingService;

}
