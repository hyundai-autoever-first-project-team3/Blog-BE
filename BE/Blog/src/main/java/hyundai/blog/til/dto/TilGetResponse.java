package hyundai.blog.til.dto;

import hyundai.blog.comment.dto.CommentDetailDto;
import hyundai.blog.comment.entity.Comment;
import hyundai.blog.til.entity.Til;
import lombok.Getter;

import java.util.List;

@Getter
public class TilGetResponse {

    private Til til;
    private List<CommentDetailDto> commentDetailDtos;
    private Long commentCounts;
    private boolean isLiked;
    private Long likeCounts;

    public TilGetResponse(Til til, List<CommentDetailDto> commentDetailDtos, Long likeCounts,
            boolean isLiked) {
        this.til = til;
        this.commentDetailDtos = commentDetailDtos;
        this.commentCounts = (long) commentDetailDtos.size();
        this.isLiked = isLiked;
        this.likeCounts = likeCounts;
    }


}
