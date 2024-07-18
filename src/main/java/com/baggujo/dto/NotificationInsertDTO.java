package com.baggujo.dto;

import lombok.Data;

@Data
public class NotificationInsertDTO {
    private long memberId;
    private String text;
    private String link;
    private boolean isChat;
    private long tradeId;


    public NotificationInsertDTO(long memberId, String text, String link, boolean isChat, long tradeId) {
        this.memberId = memberId;
        this.text = text;
        this.link = link;
        this.isChat = isChat;
        this.tradeId = tradeId;
    }

    public NotificationInsertDTO(long memberId, String text, String link) {
        this.memberId = memberId;
        this.text = text;
        this.link = link;
        this.isChat = false;
        this.tradeId = 0L;
    }

}
