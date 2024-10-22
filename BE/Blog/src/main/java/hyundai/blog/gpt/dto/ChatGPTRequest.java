package hyundai.blog.gpt.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

    public static ChatGPTRequest createCodingTestPrompt(String algorithm, String model) {
        String prompt = String.format("Please provide 3 coding test questions that fit the subject of the %s on the public platform. Each problem should have a title, difficulty (easy, medium, hard), kind, and a link to the problem. Pick one question for each level of difficulty. kind는 문제 사이트의 이름을 의미 해. Choose a random question from the sites on https://programmers.co.kr/, https://www.acmicpc.net/ . description은 주지마. Title 줄 때 따옴표는 제거해 줘, easy/medium/hard 순서대로 문제 출력해", algorithm);

        return new ChatGPTRequest(model, prompt);
    }
}
