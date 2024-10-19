package hyundai.blog.comment.controller;

import hyundai.blog.comment.dto.CommentCreateRequest;
import hyundai.blog.comment.dto.CommentUpdateRequest;
import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 1) post 매핑
    @PostMapping("/comment")
    // 2) 리턴 타입 정의 + 함수명

    // 3) 어떤 게 들어나요???????? -> paramete에 들어갈 값 RequestBody
    public ResponseEntity<?> saveComment(
            @RequestBody CommentCreateRequest request
    ){
        // 4) 누군가한테 이걸 넘겨줘야해
        Comment savedComment = commentService.save(request);

        // 5)
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequest request
    ) {
        Comment updatedComment = commentService.update(id, request);

        return ResponseEntity.ok(updatedComment);
    }

}
