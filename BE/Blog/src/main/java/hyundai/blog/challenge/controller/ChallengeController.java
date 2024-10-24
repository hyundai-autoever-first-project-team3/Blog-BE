package hyundai.blog.challenge.controller;


import hyundai.blog.challenge.dto.ChallengePreviewDto;
import hyundai.blog.problem.entity.Problem;
import hyundai.blog.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/challenges/{id}")
    public ResponseEntity<?> getThreeChallengeTil(@PathVariable Long id) {
        List<Problem> getchallengeTils = challengeService.getChallengeTils(id);

        return ResponseEntity.ok(getchallengeTils);
    }

    @GetMapping("/challenges")
    public ResponseEntity<?> getChallenge(
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<ChallengePreviewDto> challengePreviewDto = challengeService.getChallengePreview(page);

        return ResponseEntity.ok(challengePreviewDto);
    }

}
