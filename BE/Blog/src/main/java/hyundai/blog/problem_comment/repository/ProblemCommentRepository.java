package hyundai.blog.problem_comment.repository;

import hyundai.blog.problem.entity.Problem;
import hyundai.blog.problem_comment.entity.ProblemComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemCommentRepository extends JpaRepository<ProblemComment, Long> {
    Page<ProblemComment> findAll(Pageable pageable);
}
