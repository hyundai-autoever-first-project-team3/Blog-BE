package hyundai.blog.problem.service;

import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.problem.entity.Problem;
import hyundai.blog.problem.exception.ProblemIdNotFoundException;
import hyundai.blog.problem.repository.ProblemRepository;
import hyundai.blog.problem_comment.dto.ProblemComments;
import hyundai.blog.problem_comment.dto.ProblemCommentsPreviewDto;
import hyundai.blog.problem_comment.entity.ProblemComment;
import hyundai.blog.problem_comment.repository.ProblemCommentRepository;
import hyundai.blog.util.MemberResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private static final int SIZE = 10;

    private final ProblemRepository problemRepository;
    private final ProblemCommentRepository problemCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ProblemCommentsPreviewDto getProblemInfo(Long problemId, int page) {
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
}
