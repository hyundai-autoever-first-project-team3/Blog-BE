package hyundai.blog.member.dto;

import hyundai.blog.member.entity.MemberRole;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberDto extends User {

    private final String email;
    private final String name;
    private final String profileImage;
    private final String nickname;
    private final String social;
    private final List<MemberRole> memberRoleList;

    // JWT Claims로부터 MemberDto 생성
    public MemberDto(Map<String, Object> claims) {
        super((String) claims.get("email"), "", mapToGrantedAuthorities((String) claims.get("role")));

        String roleString = (String) claims.get("role");
        MemberRole role = MemberRole.valueOf(roleString);  // String을 Enum으로 변환

        this.email = (String) claims.get("email");
        this.name = (String) claims.get("name");
        this.profileImage = (String) claims.get("profileImage");
        this.nickname = (String) claims.get("name");  // JWT에서 별도의 닉네임 필드가 없으므로 name을 사용
        this.social = (String) claims.get("social");
        this.memberRoleList = List.of(role);  // MemberRole 리스트로 저장
    }

    // role을 GrantedAuthority로 변환하는 메소드
    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
