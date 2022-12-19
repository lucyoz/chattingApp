package com.likelion.app_chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
@Slf4j
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

    @GetMapping("/room")
    public String showRoom(){
        return "chat/room";
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<ChatMessage> writeMessage(@RequestBody WriteMessageRequest req){
        ChatMessage message = new ChatMessage(req.getAuthorName(), req.getContent());

        chatMessages.add(message);

        return new RsData("S-1","메세지가 작성되었습니다.", new WriteMessageResponse(message.getId()));
    }

    @AllArgsConstructor
    @Getter
    public static class MessagesRequest{
        //@RequestParam(defaultValue = "0")
        private final Long fromId;
    }

    @AllArgsConstructor
    @Getter
    public static class MessagesResponse{
        private final List<ChatMessage> messages;
        private long count;
    }

    @GetMapping("/messages")
    @ResponseBody
    public RsData<MessagesResponse> messages(MessagesRequest req){
        List<ChatMessage> messages = chatMessages;

        //번호가 입력되었다면
        if(req.fromId!=null){
            //해당 번호의 채팅메세지가 전체 리스트에서의 배열 인덱스 번호를 구한다.
            //없다면 -1
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId)
                    .findFirst()
                    .orElse(-1);
            if (index!=-1){
                //만약에 index가 있다면, 0번 부터 index번까지 제거한 리스트를 만든다.
                messages = messages.subList(index + 1, messages.size());
            }
        }

        return new RsData("S-1","성공",new MessagesResponse(messages, messages.size()) );
    }


}
