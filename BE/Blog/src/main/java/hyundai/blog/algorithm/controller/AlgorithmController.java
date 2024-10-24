package hyundai.blog.algorithm.controller;

import hyundai.blog.algorithm.dto.AlgorithmListResponse;
import hyundai.blog.algorithm.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @GetMapping("/algorithms")
    public ResponseEntity<?> getAlgorithmLists() {
        AlgorithmListResponse algorithmListResponse = algorithmService.getAlgorithms();

        return ResponseEntity.ok(algorithmListResponse);
    }
}
