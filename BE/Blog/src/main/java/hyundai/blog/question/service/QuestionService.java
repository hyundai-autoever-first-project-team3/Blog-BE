package hyundai.blog.question.service;

import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.question.dto.QuestionCreateRequest;
import hyundai.blog.question.dto.QuestionCreateResponse;
import hyundai.blog.question.dto.QuestionUpdateRequest;
import hyundai.blog.question.dto.QuestionUpdateResponse;
import hyundai.blog.question.entity.Question;
import hyundai.blog.question.exception.QuestionIdNotFoundException;
import hyundai.blog.question.repository.QuestionRepository;
import hyundai.blog.util.JwtTokenProvider;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final JwtTokenProvider jwtTokenProvider;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public QuestionCreateResponse createQuestion(QuestionCreateRequest request) {
        // memberId가 존재하는 지 확인
        validateMemberIdExists(request.memberId());

        // challengeTilId가 존재하는 지 확인
        validateChallengeTilIdExists(request.challengeTilId());

        // 1) request를 통해 Question Entity 생성
        Question question = Question.builder()
                .challengeTilId(request.challengeTilId())
                .memberId(request.memberId())
                .title(request.title())
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 2) questionEntity 저장
        questionRepository.save(question);

        return QuestionCreateResponse.of("question 생성 성공!");
    }

    @Transactional
    public QuestionCreateResponse updateQuestion(Long questionId, QuestionUpdateRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionIdNotFoundException::new);

        question.update(request);

        return QuestionCreateResponse.of("question 업데이트 성공!");
    }


    private void validateMemberIdExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberIdNotFoundException();
        }
    }

    private void validateChallengeTilIdExists(Long challengeTilId) {
        /* [TODO] challengeTil, challenge 완셩되면 구현하기*/
    }
}
