package hyundai.blog.problem_comment.dto;

import hyundai.blog.problem.entity.Problem;
import hyundai.blog.problem_comment.entity.ProblemComment;
import java.time.LocalDateTime;
import java.util.List;

public record ProblemCommentsPreviewDto(
        String siteKinds,
        String problemTitle,
        List<ProblemComments> problemCommentsList
        ) {
        public ProblemCommentsPreviewDto of(
                Problem problem, List<ProblemComments> problemCommentsList
        ) {
            return new ProblemCommentsPreviewDto(
                    problem.getSiteKinds(),
                    problem.getTitle(),
                    problemCommentsList
            );
        }
}
