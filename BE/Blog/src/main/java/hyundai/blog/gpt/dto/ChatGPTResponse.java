package hyundai.blog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }

    public List<ChallengeTilGPTDto> extractCodingTestProblems(){

        List<ChallengeTilGPTDto> results = new ArrayList<>();

        for (Choice choice : choices) {
            String content = choice.getMessage().getContent();  //

            // 1) 개행을 기준으로 잘라
            String[] lines = content.split("\n");

            Map<String, String> valueMap = new HashMap<>();

            for(int i=0; i<lines.length; i++){


                // 3) values[0] = Link , values[1] = https://naver.com
                String[] values = lines[i].split(":");

                // 4) valueMap에 각각 key-value 넣기
                valueMap.put(values[0], values[1].trim());



            }

            // key-value를 조회하여 dto 생성
            ChallengeTilGPTDto dto = new ChallengeTilGPTDto();
            dto.setLevel(valueMap.get("Difficulty"));
            dto.setSite(valueMap.get("Link"));
            dto.setTitle(valueMap.get("Title"));

            results.add(dto);
        }

        return results;
    }

//    public List<ChallengeGPTDto> extractCodingTestProblems() {
//        List<ChallengeGPTDto> problems = new ArrayList<>();
//        for (Choice choice : choices) {
//            String content = choice.getMessage().getContent();
//
//            // 1) 개행을 기준으로 자르고
//
//            // 2)
//
//            ChallengeGPTDto dto = new ChallengeGPTDto();
//            dto.setTitle(value);
//        }
//        return problems;
//    }
}
