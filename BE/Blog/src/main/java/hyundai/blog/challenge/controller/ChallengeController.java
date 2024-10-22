package hyundai.blog.challenge.controller;


import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.challenge.entity.ChallengeTil;
import hyundai.blog.challenge.repository.ChallengeTilRepository;
import hyundai.blog.challenge.service.ChallengeService;
import hyundai.blog.gpt.dto.ChatGPTRequest;
import hyundai.blog.gpt.dto.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChallengeController {


    private final ChallengeService challengeService;

    @GetMapping("/challenge_create")
    public ResponseEntity<?> createChallenge() {


        // 응답 데이터를 사용하여 Challenge 생성
        challengeService.createChallenge();

        return ResponseEntity.ok("");
    }

//    'CHALLENGE_TIL` 테이블에 존재하는 것들 중, `challenge_id` 가
//    {challengeId} 인 것들을 모두 가져온다 -> [ 3개 ] 가 가져와진다.
    @GetMapping("/challenge/{id}")
    public ResponseEntity<?> getThreeChallengeTil(@PathVariable Long id) {
        List<ChallengeTil> getchallengeTils= challengeService.getChallengeTils(id);

        return ResponseEntity.ok(getchallengeTils);
    }


}
