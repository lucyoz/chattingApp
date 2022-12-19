package com.likelion.app_chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @AllArgsConstructor
    @Getter
    public static class WriteMessageRequest{
        private final String authorName;
        private final String content;
    }

    @AllArgsConstructor
    @Getter
    public static  class WriteMessageResponse{
        private final long id;
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<ChatMessage> writeMessage(@RequestBody WriteMessageRequest req){
        ChatMessage message = new ChatMessage(req.getAuthorName(), req.getContent());

        chatMessages.add(message);

        return new RsData("S-1","메세지가 작성되었습니다.", new WriteMessageResponse(message.getId()));
    }

}
