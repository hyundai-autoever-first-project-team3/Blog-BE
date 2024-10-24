package hyundai.blog.algorithm.dto;

public record AlgorithmCountDto(
        String name,
        int count
) {

    public static AlgorithmCountDto of(String name, int count) {
        return new AlgorithmCountDto(name, count);
    }

}
