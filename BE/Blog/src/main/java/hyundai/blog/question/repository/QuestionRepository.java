package hyundai.blog.question.repository;

import hyundai.blog.question.entity.Question;
import hyundai.blog.question_comment.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
