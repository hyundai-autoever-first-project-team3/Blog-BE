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
        String prompt = String.format("Please provide 3 coding test questions that fit the subject of the %s on the public platform. Each problem should have a title, difficulty (easy, medium, hard), and a link to the problem. Pick one question for each level of difficulty. Choose a random question from the sites on https://programmers.co.kr/, https://www.acmicpc.net/ . description은 주지마. Title 줄 때 따옴표는 제거해 줘. Title 값은 항상 한국어로 번역해 줘.", algorithm);
        return new ChatGPTRequest(model, prompt);
    }
}
