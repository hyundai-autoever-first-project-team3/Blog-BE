package hyundai.blog.question_comment.entity;

import hyundai.blog.question_comment.dto.QuestionCommentUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QUESTION_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class QuestionComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "question_id")
    private Long questionId;
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(QuestionCommentUpdateRequest request) {
        this.content = request.content();
        this.updatedAt = LocalDateTime.now();
    }
}
