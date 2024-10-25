package hyundai.blog.problem.controller;

import hyundai.blog.problem.service.ProblemService;
import hyundai.blog.problem_comment.dto.ProblemCommentsPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/challenges/{challengeId}/problems/{problemId}")
    public ResponseEntity<?> getProblem(
            @PathVariable Long challengeId,
            @PathVariable Long problemId,
            @RequestParam(defaultValue = "0") int page
    ) {
        ProblemCommentsPreviewDto problemCommentsPreviewDto = problemService.getProblemInfo(
                problemId, page);

        return ResponseEntity.ok(problemCommentsPreviewDto);
    }
}
