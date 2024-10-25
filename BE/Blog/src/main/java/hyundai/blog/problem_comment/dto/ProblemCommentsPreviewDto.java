package hyundai.blog.problem_comment.dto;

import hyundai.blog.problem.entity.Problem;
import hyundai.blog.problem_comment.entity.ProblemComment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record ProblemCommentsPreviewDto(
        String siteKinds,
        String problemTitle,
        Page<ProblemComments> problemCommentsList
) {
    public static ProblemCommentsPreviewDto of(
            Problem problem, Page<ProblemComments> problemCommentsList
    ) {
        return new ProblemCommentsPreviewDto(
                problem.getSiteKinds(),
                problem.getTitle(),
                problemCommentsList
        );
    }
}
