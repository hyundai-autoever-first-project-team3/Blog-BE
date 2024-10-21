package hyundai.blog.question.controller;

import hyundai.blog.question.dto.QuestionCreateRequest;
import hyundai.blog.question.dto.QuestionCreateResponse;
import hyundai.blog.question.dto.QuestionDeleteResponse;
import hyundai.blog.question.dto.QuestionUpdateRequest;
import hyundai.blog.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping("/challenges/{challengeId}/{challengeTilId}")
    public ResponseEntity<?> createQuestion(
            @RequestBody QuestionCreateRequest request,
            @PathVariable Long challengeId,
            @PathVariable Long challengeTilId) {

        QuestionCreateResponse response = questionService.createQuestion(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/challenges/{challengeId}/{challengeTilId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(
            @RequestBody QuestionUpdateRequest request,
            @PathVariable Long challengeId,
            @PathVariable Long challengeTilId,
            @PathVariable Long questionId) {

        QuestionCreateResponse response = questionService.updateQuestion(questionId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/challenges/{challengeId}/{challengeTilId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @PathVariable Long challengeId,
            @PathVariable Long challengeTilId,
            @PathVariable Long questionId) {
        QuestionDeleteResponse response = questionService.deleteQuestion(questionId);

        return ResponseEntity.ok(response);
    }


}
