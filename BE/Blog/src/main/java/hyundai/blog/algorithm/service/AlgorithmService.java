package hyundai.blog.algorithm.service;

import hyundai.blog.algorithm.dto.AlgorithmListResponse;
import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.algorithm.repository.AlgorithmRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

    public AlgorithmListResponse getAlgorithms() {
        List<Algorithm> algorithmList = algorithmRepository.findAll();

        return AlgorithmListResponse.of(algorithmList);
    }
}
