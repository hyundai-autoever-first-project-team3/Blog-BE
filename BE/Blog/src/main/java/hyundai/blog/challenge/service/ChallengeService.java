package hyundai.blog.challenge.service;

import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.algorithm.repository.AlgorithmRepository;
import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.challenge.entity.ChallengeTil;
import hyundai.blog.challenge.repository.ChallengeRepository;
import hyundai.blog.challenge.repository.ChallengeTilRepository;
import hyundai.blog.gpt.dto.ChallengeTilGPTDto;
import hyundai.blog.gpt.dto.ChatGPTRequest;
import hyundai.blog.gpt.dto.ChatGPTResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor
public class ChallengeService {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;


    private final RestTemplate template;

    private final ChallengeRepository challengeRepository;
    private final ChallengeTilRepository challengeTilRepository;
    private final AlgorithmRepository algorithmRepository;


    @Transactional
    public void createChallenge() {

        // 1) 알고리즘 DB에서 오래된 순으로 정렬 후, 제일 오래된 값을 가져온다.
        Algorithm algorithm = algorithmRepository.findFirstByOrderByUsedAtAsc();


        // 2) ChatGPT API 요청 생성 ( algorithm + model )
        ChatGPTRequest request = ChatGPTRequest.createCodingTestPrompt(algorithm.getEngClassification(), model);

        // 3) ChatGPT API 호출하여 응답 받기
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);

        // 4) algorithm userAt 업데이트
        algorithm.updateUsedAt();

        // 5) Challenge entity
        Challenge challenge = Challenge.builder()
                .title("오늘 날짜")
                .createdAt(LocalDateTime.now())
                .algorithm(algorithm.getEngClassification())
                .views(0L)
                .build();

        // 6) Challenge entity를 save
        Challenge savedChallenge = challengeRepository.save(challenge);


        // 6) 3개의 ChallengeTil을 만들어야 해.
        // -> choice를 가지고, 3개의 뭔가를 만들어야 해
        // -> ChallengeTilGPTDto
        List<ChallengeTilGPTDto> problems = chatGPTResponse.extractCodingTestProblems();


        for (ChallengeTilGPTDto problem : problems) {

            // 7) Challenge Til entity 를 만들어줬다.
            ChallengeTil challengeTil = ChallengeTil.builder()
                    .title(problem.getTitle())
                    .challengeId(savedChallenge.getId())
                    .level(problem.getLevel())
                    .site(problem.getSite())
                    .createdAt(LocalDateTime.now())
                    .build();

            // 8) challenge til repository에 저장
            challengeTilRepository.save(challengeTil);
        }

    }

}
