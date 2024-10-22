package hyundai.blog.security.filter;

import hyundai.blog.member.dto.MemberDto;
import hyundai.blog.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. 쿠키에서 accessToken 찾기
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if (cookies != null) {
            // 쿠키 배열에서 accessToken을 찾음
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                accessToken = accessTokenCookie.get().getValue();
            }
        }

        // 2. accessToken이 존재할 때 JWT 검증
        if (accessToken != null) {
            try {
                // JWT 검증 및 클레임 추출
                Map<String, Object> claims = jwtTokenProvider.validateToken(accessToken);

                MemberDto memberDto = new MemberDto(claims);

                // 인증 토큰 생성 및 SecurityContext 설정
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        memberDto, "", memberDto.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                log.info("유저 액세스 성공 accessToken: {}", accessToken);

            } catch (Exception e) {
                // JWT 검증 실패 시 예외 처리
                log.error("JWT 검증 오류: {}", e.getMessage());
            }
        } else {
            log.info("accessToken 쿠키가 없습니다.");
        }

        // 필터 체인의 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}
