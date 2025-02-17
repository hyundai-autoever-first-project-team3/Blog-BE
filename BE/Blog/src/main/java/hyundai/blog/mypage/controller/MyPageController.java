package hyundai.blog.mypage.controller;

import hyundai.blog.mypage.dto.StatisticViewDto;
import hyundai.blog.mypage.service.MyPageService;
import hyundai.blog.til.dto.TilPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/tils")
    public ResponseEntity<?> getMyPageTils(
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<TilPreviewDto> tilList = myPageService.getTils(page);

        return ResponseEntity.ok(tilList);
    }

    @GetMapping("/mypage/likes")
    public ResponseEntity<?> getMyPageLikes(
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<TilPreviewDto> likedTilList = myPageService.getLikedTils(page);

        return ResponseEntity.ok(likedTilList);
    }

    @GetMapping("/mypage/statistics")
    public ResponseEntity<?> getMyPageStatistics() {
        StatisticViewDto statisticViewDto = myPageService.getStatistics();

        return ResponseEntity.ok(statisticViewDto);
    }


}
