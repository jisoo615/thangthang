package com.thang.thang.domain.auction.controller;

import com.thang.thang.domain.auction.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    // 클라이언트가 /pub/chat/message 로 보내면 이 메서드 실행 (클라이언트 -> 서버)
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        System.out.println("도착한 메시지: " + message.getMessage());

        // /sub/chat/room/{roomId} 경로로 구독자들에 발송 (서버 -> 클라이언트)
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
