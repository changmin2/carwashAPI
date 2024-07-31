package com.example.carwash.controller.fireabse;

import com.example.carwash.service.fireabase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/firebase")
public class FireabaseController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage() throws IOException {
        System.out.println("fcm start");
        firebaseCloudMessageService.sendMessageTo(
                "cx2kH3OtOkK5gk7qe2z08A:APA91bGNcKXp4VUI7xcdfftguGy4ox_CsT7v8h4xxp4qBxu2popFdtsxVpa96jUCeNpdPIZNQpkdSr93dCpH54BrOGwtMz-ktGsUTN5Y5doGKCr913ukOTLFDqDfErVa6hW4ez_x_eSy",
                "spring test",
                "spring testtest");
        System.out.println("fcm finish");
        return ResponseEntity.ok().build();
    }
}
