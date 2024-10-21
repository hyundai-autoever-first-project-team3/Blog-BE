package hyundai.blog.question_comment.service;

import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.question.exception.QuestionIdNotFoundException;
import hyundai.blog.question.repository.QuestionRepository;
import hyundai.blog.question_comment.dto.QuestionCommentCreateRequest;
import hyundai.blog.question_comment.dto.QuestionCommentCreateResponse;
import hyundai.blog.question_comment.dto.QuestionCommentUpdateRequest;
import hyundai.blog.question_comment.dto.QuestionCommentUpdateResponse;
import hyundai.blog.question_comment.entity.QuestionComment;
import hyundai.blog.question_comment.exception.QuestionCommentIdNotFoundException;
import hyundai.blog.question_comment.exception.QuestionCommentMemberIdValidationException;
import hyundai.blog.question_comment.repository.QuestionCommentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionCommentService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final QuestionCommentRepository questionCommentRepository;

    @Transactional
    public QuestionCommentCreateResponse createQuestionComment(
            Long questionId,
            QuestionCommentCreateRequest request) {

        // 1) question id 검증
        validateQuestionIdExists(questionId);

        // 2) member id 검증
        validateMemberIdExists(request.memberId());

        // 3) question comment 엔티티 생성
        QuestionComment questionComment = QuestionComment.builder()
                .memberId(request.memberId())
                .questionId(questionId)
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 4) question comment entity 저장
        questionCommentRepository.save(questionComment);

        return QuestionCommentCreateResponse.of("question comment 생성 완료");
    }


    @Transactional
    public QuestionCommentUpdateResponse updateQuestionComment(
            Long questionCommentId,
            QuestionCommentUpdateRequest request) {

        validateMemberIdExists(request.memberId());

        QuestionComment questionComment = questionCommentRepository.findById(questionCommentId)
                .orElseThrow(QuestionCommentIdNotFoundException::new);

        validateQuestionCommentMemberId(questionComment.getMemberId(), request.memberId());

        questionComment.update(request);

        return QuestionCommentUpdateResponse.of("question comment 수정 완료");
    }

    private void validateQuestionIdExists(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionIdNotFoundException();
        }
    }

    private void validateMemberIdExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberIdNotFoundException();
        }
    }

    private void validateQuestionCommentMemberId(Long questionCommentMemberId, Long memberId) {
        if (!questionCommentMemberId.equals(memberId)) {
            throw new QuestionCommentMemberIdValidationException();
        }
    }

}
