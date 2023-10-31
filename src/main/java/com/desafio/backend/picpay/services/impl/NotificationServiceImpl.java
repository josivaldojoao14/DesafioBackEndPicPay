package com.desafio.backend.picpay.services.impl;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.NotificationDto;
import com.desafio.backend.picpay.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    public void sendNotification(User user, String message) {
        String email = user.getEmail();
        NotificationDto notificationRequest = new NotificationDto(email, message);

        System.out.println("Transação realizada com sucesso");
    }
}
