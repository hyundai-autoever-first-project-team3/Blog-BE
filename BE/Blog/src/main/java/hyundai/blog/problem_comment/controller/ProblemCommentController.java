package hyundai.blog.problem_comment.controller;

import hyundai.blog.problem_comment.dto.*;
import hyundai.blog.problem_comment.service.ProblemCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProblemCommentController {

    private final ProblemCommentService problemCommentService;

    @PostMapping("/problem_comment")
    public ResponseEntity<?> createProblemComment(
            @RequestBody ProblemCommentCreateRequest request) {

        ProblemCommentCreateResponse response = problemCommentService.createProblemComment(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/problem_comments/{problemCommentId}")
    public ResponseEntity<?> updateQuestion(
            @RequestBody ProblemCommentUpdateRequest request,
            @PathVariable Long problemCommentId) {

        ProblemCommentUpdateResponse response = problemCommentService.updateProblemComment(
                problemCommentId,
                request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/problem_comments/{problemCommentId}")
    public ResponseEntity<?> deleteQuestion(
            @PathVariable Long problemCommentId) {
        ProblemCommentDeleteResponse response = problemCommentService.deleteProblemComment(
                problemCommentId);

        return ResponseEntity.ok(response);
    }

}
