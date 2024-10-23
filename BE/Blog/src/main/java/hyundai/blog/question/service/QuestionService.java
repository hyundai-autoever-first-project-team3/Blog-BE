package hyundai.blog.question.service;

import hyundai.blog.challenge.entity.ChallengeTil;
import hyundai.blog.challenge.exception.ChallengeTilIdNotFoundException;
import hyundai.blog.challenge.repository.ChallengeTilRepository;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.question.dto.*;
import hyundai.blog.question.entity.Question;
import hyundai.blog.question.exception.QuestionIdNotFoundException;
import hyundai.blog.question.repository.QuestionRepository;
import hyundai.blog.question_comment.dto.QuestionCommentViewDto;
import hyundai.blog.question_comment.entity.QuestionComment;
import hyundai.blog.question_comment.repository.QuestionCommentRepository;
import hyundai.blog.util.JwtTokenProvider;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final JwtTokenProvider jwtTokenProvider;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final ChallengeTilRepository challengeTilRepository;
    private final QuestionCommentRepository questionCommentRepository;
    private static final int SIZE = 12;
    private final CommentRepository commentRepository;

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

    public QuestionDeleteResponse deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionIdNotFoundException::new);

        questionRepository.delete(question);

        return QuestionDeleteResponse.of("question 삭제 성공");
    }


    private void validateMemberIdExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberIdNotFoundException();
        }
    }

    private void validateChallengeTilIdExists(Long challengeTilId) {
        /* [TODO] challengeTil, challenge 완셩되면 구현하기*/
    }

    public Page<QuestionsPreviewDto> getQuestions(Long challengeTilId, int page) {
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 2) 페이지 요청에 따른 모든 Question 조회
        Page<Question> questionPage = questionRepository.findAll(pageable);

        List<QuestionsPreviewDto> questionsPreviewDtos = questionPage.stream()
                .map(question -> {
                    Member member = memberRepository.findById(question.getMemberId()).orElseThrow(MemberIdNotFoundException::new);

                    ChallengeTil challengeTil = challengeTilRepository.findById(challengeTilId).orElseThrow(ChallengeTilIdNotFoundException::new);

                    Long commentsCount = questionCommentRepository.countByQuestionId(question.getId());


                    return QuestionsPreviewDto.of(member, challengeTil, question, commentsCount);
                }).toList();

        return new PageImpl<>(questionsPreviewDtos, pageable, questionPage.getTotalElements());
    }

    @Transactional
    public QuestionDetailDto getQuestion(Long questionsId) {
        //questionCommentViewDto 생성을 위해
        //Member member, QuestionComment questionComment 가져오기
        Question question = questionRepository.findById(questionsId)
                .orElseThrow(QuestionIdNotFoundException::new);

        List<QuestionComment> questionComment = questionCommentRepository.findAllByQuestionId(questionsId);

        //questionCommentViewDto 생성 <List>형태로 담아주기
        List<QuestionCommentViewDto> questionCommentViewDtos = questionComment.stream()
                .map(questionComments -> {
                    Member member = memberRepository.findById(questionComments.getMemberId())
                            .orElseThrow(MemberIdNotFoundException::new);

                    return QuestionCommentViewDto.of(member, questionComments);
                }).toList();


        //questionDetailDto 생성을 위해
        //ChallengeTil challengeTil, Member member,Question question,Long commentCounts,
        //List<QuestionCommentViewDto> questionCommentViewDtoList 가져오기

        ChallengeTil challengeTil = challengeTilRepository.findById(question.getChallengeTilId())
                .orElseThrow(ChallengeTilIdNotFoundException::new);

        Member member = memberRepository.findById(question.getMemberId())
                .orElseThrow(MemberIdNotFoundException::new);

        Long commentsCounts = questionCommentRepository.countByQuestionId(questionsId);

        //questionCommentDto를 이용해서 questionDetailDto 생성
        return QuestionDetailDto.of(challengeTil, member, question, commentsCounts, questionCommentViewDtos);
    }
}
