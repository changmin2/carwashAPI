package com.example.carwash.component;

import com.example.carwash.service.fireabase.FirebaseCloudMessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FirebaseUtil {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Async
    public void sendToMessage(String title,String body,String token,String route,String routeId) throws IOException {
        firebaseCloudMessageService.sendMessageTo(token,title,body,routeId);
    }
}
