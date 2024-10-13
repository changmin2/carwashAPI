package com.example.carwash.service.fireabase;

import com.example.carwash.domain.firebase.FcmMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/" +
            "carwash-68d25/messages:send";
    private final ObjectMapper objectMapper;
    static int id = 0;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        System.out.println("푸쉬 알림 발송 시작");
        String message = makeMessage(targetToken, title, body);
        System.out.println("푸쉬 알림 메시지: "+message.toString());
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        System.out.println("푸쉬 알림 메시지 요청");
        Response response = client.newCall(request).execute();
        System.out.println("푸쉬 알림 메시지 응답");
        System.out.println(response.body().string());
        System.out.println("푸쉬 알림 발송 종료");
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {

        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        try {

            System.out.println("푸쉬 알림 토큰 가져오기");
            String firebaseConfigPath = "firebase/carwash-68d25-67d8daf52c0c.json";

            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
            System.out.println("푸쉬 알림 토큰 만료 검사");
            googleCredentials.refreshIfExpired();
            System.out.println("푸쉬 알림 토큰 가져오기 완료");

            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e){
            e.printStackTrace();
            throw new IOException();
        }
    }
}
