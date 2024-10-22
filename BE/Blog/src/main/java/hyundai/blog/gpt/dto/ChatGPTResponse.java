package hyundai.blog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private Message message;
    }

    public List<ChallengeTilGPTDto> extractCodingTestProblems() {
        List<ChallengeTilGPTDto> results = new ArrayList<>();

        for (Choice choice : choices) {
            String content = choice.getMessage().getContent();
            System.out.println("content : \n " + content + "\n-------");

            // 개행을 기준으로 잘라
            String[] lines = content.split("\n");

            // 각 문제를 배열로 만듬
            String[] first = {lines[0].substring(3), lines[1], lines[2], lines[3]};
            String[] second = {lines[5].substring(3), lines[6], lines[7], lines[8]};
            String[] third = {lines[10].substring(3), lines[11], lines[12], lines[13]};

            List<String[]> parts = new ArrayList<>();
            parts.add(first);
            parts.add(second);
            parts.add(third);

            System.out.println("배열 출력:");
            System.out.println(Arrays.toString(first));
            System.out.println(Arrays.toString(second));
            System.out.println(Arrays.toString(third));

            for (String[] part : parts) {
                Map<String, String> valueMap = new HashMap<>();

                for (String partValue : part) {
                    int splitIndex = partValue.indexOf(":");

                    // ':'이 없으면 해당 줄을 건너뜀
                    if (splitIndex == -1) {
                        continue;
                    }

                    String value1 = partValue.substring(0, splitIndex).trim();
                    String value2 = partValue.substring(splitIndex + 1).trim();

                    valueMap.put(value1, value2);
                    System.out.println(String.format("value1 : %s , value2 : %s", value1, value2));
                }

                ChallengeTilGPTDto dto = new ChallengeTilGPTDto();
                dto.setLevel(valueMap.get("Difficulty"));
                dto.setSite(valueMap.get("Link"));
                dto.setSiteKinds(valueMap.get("Kind"));
                dto.setTitle(valueMap.get("Title"));


                results.add(dto);
            }
        }

        System.out.println("추출된 결과 크기 : " + results.size());
        return results;
    }

}
