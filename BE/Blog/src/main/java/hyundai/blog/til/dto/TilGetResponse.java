package hyundai.blog.til.dto;

import hyundai.blog.comment.entity.Comment;
import hyundai.blog.til.entity.Til;
import lombok.Getter;

import java.util.List;

@Getter
public class TilGetResponse {
    private Long memberId;
    private String language;
    private String site;
    private String algorithm;
    private String title;
    private String tag;
    private String link;
    private String codeContent;
    private String content;

    private List<Comment> comments;

    public TilGetResponse(Til til, List<Comment> comments) {
        this.memberId = til.get();
        this.language = til.getLanguage();
        this.site = til.getSite();
        this.algorithm = til.getAlgorithm();
        this.title = til.getTitle();
        this.tag = til.getTag();
        this.link = til.getLink();
        this.codeContent = til.getCodeContent();
        this.content = til.getContent();
        this.comments = comments;
    }
}
