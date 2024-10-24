package hyundai.blog.problem_comment.service;

import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.problem.exception.ProblemIdNotFoundException;
import hyundai.blog.problem.repository.ProblemRepository;
import hyundai.blog.problem.entity.Problem;
import hyundai.blog.member.entity.Member;
import hyundai.blog.problem_comment.dto.*;
import hyundai.blog.problem_comment.entity.ProblemComment;
import hyundai.blog.problem_comment.repository.ProblemCommentRepository;
import hyundai.blog.util.MemberResolver;
import java.util.*;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemCommentService {

    private static final int SIZE = 10;

    private final ProblemCommentRepository problemCommentRepository;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;
    private final MemberResolver memberResolver;

    @Transactional
    public ProblemCommentCreateResponse createProblemComment(ProblemCommentCreateRequest request) {

        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) problem id가 존재하는 지 확인
        validateProblemIdExists(request.problemId());

        // 3) request로 ProblemComment 객체 생성
        ProblemComment problemComment = ProblemComment.builder()
                .problemId(request.problemId())
                .memberId(loggedInMember.getId())
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        problemCommentRepository.save(problemComment);

        log.info("problem comment 생성 완료 : {}", problemComment);

        return ProblemCommentCreateResponse.of(problemComment, "problem comment 생성 완료");
    }

    @Transactional
    public ProblemCommentUpdateResponse updateProblemComment(Long problemCommentId,
            ProblemCommentUpdateRequest request) {

        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) problem comment 조회
        ProblemComment problemComment = problemCommentRepository.findById(problemCommentId)
                .orElseThrow(ProblemIdNotFoundException::new);

        problemComment.updateContent(request.content());

        log.info("problem comment 수정 완료 : {}", problemComment);

        return ProblemCommentUpdateResponse.of(problemComment, "problem comment 수정 완료");
    }

    @Transactional
    public ProblemCommentDeleteResponse deleteProblemComment(Long problemCommentId) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) problem comment 조회
        ProblemComment problemComment = problemCommentRepository.findById(problemCommentId)
                .orElseThrow(ProblemIdNotFoundException::new);

        // 3) problem comment 삭제
        problemCommentRepository.delete(problemComment);

        log.info("problem comment 삭제 완료 : {}", problemComment);

        return ProblemCommentDeleteResponse.of(problemComment, "problem comment 삭제 완료");
    }

    @Transactional
    public ProblemCommentsPreviewDto getQuestions(Long problemId, int page) {
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 2) 페이지 요청에 따른 모든 Question 조회
        Page<ProblemComment> problemCommentPage = problemCommentRepository.findAll(pageable);

        List<ProblemComments> problemCommentsDtos = problemCommentPage.stream()
                .map(problemComment -> {
                    Member member = memberRepository.findById(problemComment.getMemberId())
                            .orElseThrow(MemberIdNotFoundException::new);

                    return ProblemComments.of(member, problemComment);
                }).toList();

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(ProblemIdNotFoundException::new);

        Page<ProblemComments> problemCommentsList = new PageImpl<>(problemCommentsDtos, pageable,
                problemCommentPage.getTotalElements());

        return ProblemCommentsPreviewDto.of(problem, problemCommentsList);
    }

    private void validateProblemIdExists(Long problemId) {
        if (!problemRepository.existsById(problemId)) {
            throw new ProblemIdNotFoundException();
        }

    }
}
