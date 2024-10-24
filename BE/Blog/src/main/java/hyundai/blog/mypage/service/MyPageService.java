package hyundai.blog.mypage.service;

import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.algorithm.exception.AlgorithmIdNotFoundException;
import hyundai.blog.algorithm.repository.AlgorithmRepository;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.gpt.dto.ChatGPTRequest;
import hyundai.blog.gpt.dto.ChatGPTResponse;
import hyundai.blog.like.entity.Like;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.mypage.dto.StatisticViewDto;
import hyundai.blog.til.dto.TilAlgorithmDto;
import hyundai.blog.til.dto.TilPreviewDto;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
import hyundai.blog.util.MemberResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private static final int SIZE = 10;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate template;

    private final MemberResolver memberResolver;
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AlgorithmRepository algorithmRepository;

    @Transactional
    public Page<TilPreviewDto> getTils(int page) {
        // 1) Member 정보 조회
        Member member = memberResolver.getCurrentMember();

        // 2) pageable 생성
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3) Member id에 해당하는 tils 조회
        Page<Til> tilPage = tilRepository.findAllByMemberId(member.getId(), pageable);

        // 4) TilPreviewDto list 생성
        List<TilPreviewDto> tilPreviewDtos = tilPage.stream()
                .map(til -> {
                    // 1) Like 개수 가져오기
                    Long likeCount = likeRepository.countByTilId(til.getId());

                    // 2) Comment 개수 가져오기
                    Long commentCount = commentRepository.countByTilId(til.getId());

                    // 1, 2, 3을 사용하여 TilPreviewDto 생성
                    return TilPreviewDto.of(til, member, commentCount, likeCount);
                })
                .toList();

        // 5) TilPreviewDto 리스트를 Page<TilPreviewDto>로 변환
        return new PageImpl<>(tilPreviewDtos, pageable, tilPage.getTotalElements());
    }

    @Transactional
    public Page<TilPreviewDto> getLikedTils(int page) {
        // 1) Member 정보 조회
        Member member = memberResolver.getCurrentMember();

        // 2) like pageable 생성
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "id"));

        // 3) like에 해당하는 객체들 가져오기
        Page<Like> likedTilsPage = likeRepository.findByMember(member, pageable);

        List<TilPreviewDto> tilPreviewDtos = likedTilsPage.stream()
                .map(like -> {
                    Til til = like.getTil();

                    Long likeCount = likeRepository.countByTilId(til.getId());

                    Long commentCount = commentRepository.countByTilId(til.getId());

                    return TilPreviewDto.of(til, member, commentCount, likeCount);
                })
                .toList();

        return new PageImpl<>(tilPreviewDtos, pageable, likedTilsPage.getTotalElements());
    }

    @Transactional
    public StatisticViewDto getStatistics() {
        // 1) 로그인 된 멤버 가져오기
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) 멤버가 작성한 게시글 수 가져오기
        Long tilsCount = tilRepository.countByMemberId(loggedInMember.getId());

        // 3) 멤버가 이번 달에 작성한 게시글 수 가져오기
        Long tilsMonthCount = tilRepository.countByMemberIdAndCreatedAtThisMonth(loggedInMember.getId());

        //로그인된 멤버가 작성한 모든 tilId를 모두 조회해서
        List<Til> tils = tilRepository.findAllByMemberId(loggedInMember.getId());

        //likeRepository에서 위의 모든 tilId에 해당하는 것들을 count

        // 각 Til의 like 개수 합산
        Long receivedLikeCount = 0L;

        // algorithm에 대한 count를 저장하는 map
        Map<String, Integer> algorithmCountMap = new HashMap<>();


        List<Algorithm> algorithmList = algorithmRepository.findAll();
        algorithmList
                .forEach(algorithm -> {
                    algorithmCountMap.put(algorithm.getEngClassification(), 0);
                });


        for (Til til : tils) {
            // 각 Til의 id로 like 개수 조회
            receivedLikeCount += likeRepository.countByTilId(til.getId());

            // 각 Til마다 존재하는 algorithmId에 해당하는 알고리즘 종류의 개수를 세도록
            Algorithm algorithm = algorithmRepository.findById(til.getAlgorithmId()).orElseThrow(AlgorithmIdNotFoundException::new);

            // 알고리즘의 이름을 가져옴
            String algorithmKind = algorithm.getEngClassification();

            if (algorithmCountMap.containsKey(algorithmKind)) {
                algorithmCountMap.put(algorithmKind, algorithmCountMap.get(algorithmKind) + 1);
            }else{
                algorithmCountMap.put(algorithmKind, 1);
            }
        }

        // 전체 개수 & 각각 알고리즘 별 푼 문제 개수를 dto에 담아서 리턴
        TilAlgorithmDto tilAlgorithmDto = new TilAlgorithmDto(tils.size(), algorithmCountMap);

        // 5) 보완해야 할 알고리즘 종류 1개랑 가장 잘하는 알고리즘 종류 1개 각각 추출 (값이 작은 순서대로)
        List<String> algorithmsToImprove = algorithmCountMap.entrySet().stream()
                // 값에 따라 정렬
                .sorted(Map.Entry.comparingByValue())
                // 키만 추출 (알고리즘 이름)
                .map(Map.Entry::getKey)
                .toList();

        String leastValue = algorithmsToImprove.get(0);
        String mostValue = algorithmsToImprove.get(algorithmsToImprove.size() - 1);

        /* [TODO]
        *    1) 부족한 부분 (leastValue)를 가지고 문제 추천 만들기
        *    2) AI 분석 (leastValue, mostValue) 가지고 ai 분석 만들기
        *       - 문제 title (문제 제목, ...)
        *       - 문제 site (백준, 프로그래머스, ...)
        *       - 문제 link (문제 링크, ...)
        * */

        // ChatGPT ai 분석 리퀘스트 생성
        ChatGPTRequest analizationRequest = ChatGPTRequest.createAIAnaliztionTestPrompt(tilAlgorithmDto, model);

        // ChatGPT ai 분석
        ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, analizationRequest, ChatGPTResponse.class);

        String chatGPTResult = chatGPTResponse.getMessage();
        System.out.println(chatGPTResult);


        // 최종 Dto 생성
        StatisticViewDto statisticViewDto = StatisticViewDto.of(tilsCount, tilsMonthCount, receivedLikeCount, tilAlgorithmDto, leastValue, mostValue);

        return statisticViewDto;
    }

}
