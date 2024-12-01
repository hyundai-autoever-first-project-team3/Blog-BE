package hyundai.blog.challenge.service;

import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.algorithm.repository.AlgorithmRepository;
import hyundai.blog.challenge.dto.ChallengePreviewDto;
import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.problem.entity.Problem;
import hyundai.blog.challenge.exception.ChallengeIdNotFoundException;
import hyundai.blog.challenge.repository.ChallengeRepository;
import hyundai.blog.problem.repository.ProblemRepository;
import hyundai.blog.gpt.dto.ChallengeTilGPTDto;
import hyundai.blog.gpt.dto.ChatGPTRequest;
import hyundai.blog.gpt.dto.ChatGPTResponse;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final ProblemRepository problemRepository;
    private final AlgorithmRepository algorithmRepository;

    @Transactional
    public void createChallenge() {
        Algorithm algorithm = algorithmRepository.findFirstByOrderByUsedAtAsc();
        if (algorithm == null) {
            throw new IllegalStateException("No algorithm available to create a challenge.");
        }

        ChatGPTRequest request = ChatGPTRequest.createCodingTestPrompt(
                algorithm.getEngClassification(), model);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request,
                ChatGPTResponse.class);

        if (chatGPTResponse == null || chatGPTResponse.extractCodingTestProblems().isEmpty()) {
            throw new IllegalStateException("ChatGPT response is invalid or contains no problems.");
        }

        algorithm.updateUsedAt();

        Challenge challenge = Challenge.builder()
                .title(String.format("%s 알고리즘", algorithm.getEngClassification()))
                .createdAt(LocalDateTime.now())
                .algorithm(algorithm.getEngClassification())
                .views(0L)
                .build();
        Challenge savedChallenge = challengeRepository.save(challenge);

        List<Problem> problemsToSave = chatGPTResponse.extractCodingTestProblems().stream()
                .map(problem -> Problem.builder()
                        .title(problem.getTitle())
                        .challengeId(savedChallenge.getId())
                        .level(problem.getLevel())
                        .site(problem.getSite())
                        .siteKinds(problem.getSiteKinds())
                        .createdAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        problemRepository.saveAll(problemsToSave);
    }


    // 매일 정각에 createChallenge 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleDailyChallengeCreation() {
        createChallenge(); // 매일 자정에 createChallenge 메서드 실행
    }

    public List<Problem> getChallengeTils(Long id) {
        List<Problem> getProblems = problemRepository.findAllByChallengeId(id);

        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(ChallengeIdNotFoundException::new);

        challenge.incrementViews();

        challengeRepository.save(challenge);

        return getProblems;
    }

    public Page<ChallengePreviewDto> getChallengePreview(int page) {
        // 1) Pageable 인터페이스 생성
        Pageable pageable = PageRequest.of(page, 12, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Challenge> challengePage = challengeRepository.findAll(pageable);

        List<ChallengePreviewDto> challengePreviewDto = challengePage.stream()
                .map(challenge -> {
                    return ChallengePreviewDto.of(challenge);
                }).toList();

        return new PageImpl<>(challengePreviewDto, pageable, challengePage.getTotalElements());
    }

}
