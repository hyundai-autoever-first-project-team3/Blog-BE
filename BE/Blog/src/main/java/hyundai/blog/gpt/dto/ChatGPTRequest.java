package hyundai.blog.gpt.dto;

import hyundai.blog.til.dto.TilAlgorithmDto;
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
        String prompt = String.format("Please provide 3 coding test questions that fit the subject of the %s on the public platform. Each problem should have a title, difficulty (Easy, Medium, Hard), kind, and a link to the problem. Pick one question for each level of difficulty. kind는 문제 사이트의 이름을 의미 해. Choose a random question from the sites on https://programmers.co.kr/, https://www.acmicpc.net/ . description은 주지마. Title 줄 때 따옴표는 제거해 줘, Easy/Medium/Hard 순서대로 문제 출력해, *표시는 출력하지 않도록 한다. kind는 주소 형식으로 주지 말고 사이트 이름으로 줘 (예를 들면, 'Baekjoon' 이렇게)", algorithm);

        return new ChatGPTRequest(model, prompt);
    }


    public static ChatGPTRequest createAIAnaliztionTestPrompt(TilAlgorithmDto tilAlgorithmDto, String model) {
        String prompt = String.format("다음은 \"Algorithm 이름\"과 그에 해당하는 성공 횟수입니다. 이 데이터를 바탕으로 각 알고리즘 카테고리에서 강점과 개선이 필요한 부분을 분석해주세요. 부족한 향상시키기 위한 학습 권장 알고리즘도 함께 제시해주세요.%s\n" +
                "두 줄로 요약해서 출력해 줘", tilAlgorithmDto.toString());

        return new ChatGPTRequest(model, prompt);
    }



    public static ChatGPTRequest createAIRecommendTestPrompt(TilAlgorithmDto tilAlgorithmDto, String model) {
        String prompt = String.format("Algorithm 이름과 성공 횟수 데이터를 바탕으로 각 알고리즘 카테고리에서 부족한 알고리즘 관련 문제를 추천해 주세요. " +
                "%s 주제에 맞는 코딩 테스트 문제를 공개 플랫폼에서 1개 추천해 주세요. 문제는 제목, 사이트 종류, 링크만 제공해 주세요. " +
                "프로그래머스(https://programmers.co.kr/) 또는 백준(https://www.acmicpc.net/)에서 임의의 문제를 선택해 주세요. " +
                "Title, Kind, Link 외에는 모든 출력값을 제거해 주세요. " +
                "* 표시도 제거해 주세요.", tilAlgorithmDto.toString());


        return new ChatGPTRequest(model, prompt);
    }

}

