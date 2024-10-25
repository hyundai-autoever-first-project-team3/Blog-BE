package hyundai.blog.like.controller;

import hyundai.blog.like.dto.LikeCreateRequest;
import hyundai.blog.like.dto.LikeCreateResponse;
import hyundai.blog.like.dto.LikeDeleteResponse;
import hyundai.blog.like.entity.Like;
import hyundai.blog.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> saveLike(
            @RequestBody LikeCreateRequest request
            ) {
        LikeCreateResponse likeCreateResponse = likeService.save(request);

        return ResponseEntity.ok(likeCreateResponse);
    }

    @DeleteMapping("/like")
    public ResponseEntity<?> deleteLike(
            @RequestBody LikeCreateRequest request
    ) {
        LikeDeleteResponse likeDeleteResponse = likeService.delete(request);
        
        return ResponseEntity.ok(likeDeleteResponse);
    }
}
