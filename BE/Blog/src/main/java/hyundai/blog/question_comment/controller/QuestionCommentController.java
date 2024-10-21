package hyundai.blog.question_comment.controller;

import hyundai.blog.question.dto.QuestionCreateRequest;
import hyundai.blog.question_comment.dto.QuestionCommentCreateRequest;
import hyundai.blog.question_comment.dto.QuestionCommentCreateResponse;
import hyundai.blog.question_comment.service.QuestionCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionCommentController {

    private final QuestionCommentService questionCommentService;

    @PostMapping("/challenges/{challengeId}/{challengeTilId}/questions/{questionId}/comment")
    public ResponseEntity<?> createQuestionComment(
            @RequestBody QuestionCommentCreateRequest request,
            @PathVariable Long challengeId,
            @PathVariable Long challengeTilId,
            @PathVariable Long questionId
    ) {
        QuestionCommentCreateResponse response = questionCommentService.createQuestionComment(
                questionId, request);

        return ResponseEntity.ok(response);
    }

}
