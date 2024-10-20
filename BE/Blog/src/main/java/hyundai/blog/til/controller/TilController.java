package hyundai.blog.til.controller;

import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.dto.TilUpdateRequest;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.service.TilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TilController {
    private final TilService tilService;

    @PostMapping("/til")
    public ResponseEntity<?> saveComment(
            @RequestBody TilCreateRequest request
    ) {
        Til savedTil = tilService.save(request);

        return ResponseEntity.ok(savedTil);
    }

    @PutMapping("/til/{id}")
    public ResponseEntity<?> updateTil(
            @PathVariable Long id,
            @RequestBody TilUpdateRequest request
    ) {

        Til updatedTil = tilService.update(id, request);

        return ResponseEntity.ok(updatedTil);
    }
}
