package hyundai.blog.question.controller;

import hyundai.blog.question.dto.*;
import hyundai.blog.question.repository.QuestionRepository;
import hyundai.blog.question.service.QuestionService;
import hyundai.blog.til.dto.TilPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;


    @PostMapping("/challenges/{challengeId}/{challengeTilId}/question")
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

    @GetMapping("/challenges/{challengeTilId}/questions")
    public ResponseEntity<?> getQuestions(
            @PathVariable Long challengeTilId,
            @RequestParam int page
    ) {
        Page<QuestionsPreviewDto> questionsPreviewDtos =  questionService.getQuestions(challengeTilId, page);
        return ResponseEntity.ok(questionsPreviewDtos);
    }

    @GetMapping("/questions/{questionsId}")
    public ResponseEntity<?> getQuestion(
            @PathVariable Long questionsId
    ) {
        QuestionDetailDto questionDetailDto = questionService.getQuestion(questionsId);

        return ResponseEntity.ok(questionDetailDto);
    }


}
