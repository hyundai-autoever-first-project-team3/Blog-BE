package hyundai.blog.mypage.dto;

public record StatisticViewDto(
        Long tilsCount,
        Long tilsMonthCount,
        Long receivedLikeCount
) {
    public static StatisticViewDto of(
            Long tilsCount,
            Long tilsMonthCount,
            Long receivedLikeCount
    ){
        return new StatisticViewDto(tilsCount, tilsMonthCount, receivedLikeCount);
    }
}
