package hyundai.blog.mypage.dto;

import hyundai.blog.til.dto.TilAlgorithmDto;

//구현, 그래프, 스택, 큐, 배열, 트리, 해시, 힙, 정렬, BFS, DFS, 그리디, DP, 완전탐색, 이분탐색, 최단경로, 탐색
public record StatisticViewDto(
        Long tilsCount,
        Long tilsMonthCount,
        Long receivedLikeCount,
        TilAlgorithmDto tilAlgorithmDto,
        String leastValue,
        String mostValue
) {
    public static StatisticViewDto of(
            Long tilsCount,
            Long tilsMonthCount,
            Long receivedLikeCount,
            TilAlgorithmDto tilAlgorithmDto,
            String leastValue,
            String mostValue
    ){
        return new StatisticViewDto(tilsCount, tilsMonthCount, receivedLikeCount, tilAlgorithmDto, leastValue, mostValue);
    }
}
