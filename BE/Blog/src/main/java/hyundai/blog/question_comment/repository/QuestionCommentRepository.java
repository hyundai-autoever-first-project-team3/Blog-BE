package hyundai.blog.question_comment.repository;

import hyundai.blog.question_comment.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

    Long countByQuestionId(Long id);

    Optional<QuestionComment> findByQuestionId(Long questionId);

    List<QuestionComment> findAllByQuestionId(Long questionsId);

}
