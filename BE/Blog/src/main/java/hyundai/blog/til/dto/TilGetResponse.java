package hyundai.blog.til.dto;

import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.comment.dto.CommentDetailDto;
import hyundai.blog.comment.entity.Comment;
import hyundai.blog.member.dto.MemberWriterDto;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import lombok.Getter;

import java.util.List;

@Getter
public class TilGetResponse {

    private Til til;
    private List<CommentDetailDto> commentDetailDtos;
    private String algorithm;
    private Long commentCounts;
    private boolean isLiked;
    private Long likeCounts;
    private Boolean isOwner;
    private MemberWriterDto memberWriterDto;

    public TilGetResponse(Til til, Member member, List<CommentDetailDto> commentDetailDtos,
            Algorithm algorithm,
            Long likeCounts,
            boolean isLiked) {
        this.til = til;
        this.algorithm = algorithm.getEngClassification();
        this.memberWriterDto = MemberWriterDto.of(member);
        this.commentDetailDtos = commentDetailDtos;
        this.commentCounts = (long) commentDetailDtos.size();
        this.isOwner = member.getId().equals(til.getMemberId());
        this.isLiked = isLiked;
        this.likeCounts = likeCounts;
    }


}
