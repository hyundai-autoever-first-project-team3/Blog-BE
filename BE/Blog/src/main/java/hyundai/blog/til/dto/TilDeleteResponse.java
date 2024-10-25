package hyundai.blog.til.dto;

public record TilDeleteResponse(
        Long tilId,
        String message
) {
    public static TilDeleteResponse of(Long tilId, String message) {
        return new TilDeleteResponse(tilId, message);
    }
}
