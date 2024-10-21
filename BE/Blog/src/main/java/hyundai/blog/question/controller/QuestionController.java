package hyundai.blog.question.controller;

import hyundai.blog.question.dto.QuestionCreateRequest;
import hyundai.blog.question.dto.QuestionCreateResponse;
import hyundai.blog.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping("/challenge/{challengeId}/{challengeTilId}")
    public ResponseEntity<?> createQuestion(
            @RequestBody QuestionCreateRequest request,
            @PathVariable Long challengeId,
            @PathVariable Long challengeTilId) {

        QuestionCreateResponse response = questionService.createQuestion(request);

        return ResponseEntity.ok(response);
    }

}
