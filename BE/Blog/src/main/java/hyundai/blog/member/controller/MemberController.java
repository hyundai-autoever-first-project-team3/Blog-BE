package hyundai.blog.member.controller;

import hyundai.blog.member.dto.MemberUpdateRequest;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<?> getMemberInfo() {
        Member member = memberService.getMemberInfo();

        return ResponseEntity.ok(member);
    }

    @PatchMapping("/member")
    public ResponseEntity<?> updateMemberInfo(
            @RequestBody MemberUpdateRequest request
    ) {
        Member member = memberService.updateMemberInfo(request);

        return ResponseEntity.ok(member);
    }

}
