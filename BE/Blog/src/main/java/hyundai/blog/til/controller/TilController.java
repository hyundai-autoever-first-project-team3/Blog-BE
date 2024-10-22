package hyundai.blog.til.controller;

import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.dto.TilGetResponse;
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

    @PutMapping("/tils/{tilId}")
    public ResponseEntity<?> updateTil(
            @PathVariable Long tilId,
            @RequestBody TilUpdateRequest request
    ) {
        Til updatedTil = tilService.update(tilId, request);

        return ResponseEntity.ok(updatedTil);
    }

    @DeleteMapping("/til/{id}")
    public void deleteTil(@PathVariable Long id) {
        tilService.delete(id);
    }

    @GetMapping("/til/{id}")
    public ResponseEntity<?> getTil(@PathVariable Long id) {
        TilGetResponse tilGetResponse = tilService.get(id);

        return ResponseEntity.ok(tilGetResponse);
    }
}
