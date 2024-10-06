package com.example.carwash.controller.fireabse;

import com.example.carwash.domain.firebase.FcmMessage;
import com.example.carwash.domain.firebase.FcmMessageDto;
import com.example.carwash.domain.member.Member;
import com.example.carwash.service.fireabase.FirebaseCloudMessageService;
import com.example.carwash.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/firebase")
public class FireabaseController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final MemberService memberService;

    @PostMapping("/fcm/sendTotalUser")
    public ResponseEntity pushMessage(@RequestBody FcmMessageDto message) throws IOException {

        List<String> fcmTokens = memberService.getRcvAlramY();
        for (String fcmToken : fcmTokens) {
            firebaseCloudMessageService.sendMessageTo(
                    fcmToken,
                    message.getTitle(),
                    message.getBody());
        }

        return ResponseEntity.ok().build();
    }
}
