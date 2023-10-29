package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDto notificationRequest = new NotificationDto(email, message);

//        ResponseEntity<String> notificationResponse = restTemplate
//                .postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.println("Erro ao enviar a notificação");
//            throw new Exception("Serviço de notificação está fora do ar!");
//        }

        System.out.println("Transação realizada com sucesso");
    }
}
