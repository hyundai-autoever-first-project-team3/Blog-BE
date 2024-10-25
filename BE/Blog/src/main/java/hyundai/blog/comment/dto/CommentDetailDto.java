package hyundai.blog.comment.dto;

import hyundai.blog.comment.entity.Comment;
import hyundai.blog.member.entity.Member;
import java.time.LocalDateTime;

public record CommentDetailDto(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long writerId,
        String writerNickname,
        String writerProfileImage
) {

    public static CommentDetailDto of(Comment comment, Member member) {
        return new CommentDetailDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                member.getId(),
                member.getNickname(),
                member.getProfileImage()
        );
    }
}
