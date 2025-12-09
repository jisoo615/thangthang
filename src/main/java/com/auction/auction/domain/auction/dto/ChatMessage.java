package com.auction.auction.domain.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String roomId; // 경매 -> auction_id
    private String sender; // 보낸 사람
    private String message;
}