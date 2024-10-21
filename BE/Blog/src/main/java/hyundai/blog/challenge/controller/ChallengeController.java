package hyundai.blog.challenge.controller;


import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.challenge.service.ChallengeService;
import hyundai.blog.gpt.dto.ChatGPTRequest;
import hyundai.blog.gpt.dto.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


}
