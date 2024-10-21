package hyundai.blog.question_comment.repository;

import hyundai.blog.question_comment.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

}
