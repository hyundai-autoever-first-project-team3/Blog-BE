package hyundai.blog.til.service;

import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class TilService {
    private final TilRepository tilRepository;

    public Til save(TilCreateRequest request) {
        Til til = Til.builder()
                .userId(request.getUserId())
                .language(request.getLanguage())
                .site(request.getSite())
                .algorithm(request.getAlgorithm())
                .title(request.getTitle())
                .tag(request.getTag())
                .link(request.getLink())
                .codeContent(request.getCodeContent())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Til savedTil = tilRepository.save(til);

        log.info("--------{}--------");

        return savedTil;
    }
}
