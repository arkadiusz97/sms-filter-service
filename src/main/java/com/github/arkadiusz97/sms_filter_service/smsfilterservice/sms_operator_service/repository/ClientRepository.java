package com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.repository;

import com.github.arkadiusz97.sms_filter_service.smsfilterservice.sms_operator_service.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByNumber(String number);
    Client findOneByNumber(String number);
}
