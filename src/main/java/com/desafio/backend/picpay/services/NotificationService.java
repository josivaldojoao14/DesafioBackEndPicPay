package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.user.User;

public interface NotificationService {
    void sendNotification(User user, String message);
}
