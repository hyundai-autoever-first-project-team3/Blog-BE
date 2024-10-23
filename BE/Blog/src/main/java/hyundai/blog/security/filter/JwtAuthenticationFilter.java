package hyundai.blog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyundai.blog.exception.BusinessExceptionResponse;
import hyundai.blog.member.dto.MemberDto;
import hyundai.blog.util.JwtTokenProvider;
import hyundai.blog.util.exception.CustomJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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

            } catch (CustomJwtException e) {
                // JWT 검증 실패 시 CustomJwtException 예외 처리
                log.error("JWT 검증 오류: {}", e.getMessage());
                setErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage(), e.getClass().getSimpleName());
                return;  // 필터 체인을 중단하고 응답을 반환
            } catch (Exception e) {
                // 기타 예외 처리
                log.error("기타 오류 발생: {}", e.getMessage());
                setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e.getClass().getSimpleName());
                return;
            }
        } else {
            log.info("accessToken 쿠키가 없습니다.");
        }

        // 필터 체인의 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message, String exception) throws IOException {
        // BusinessExceptionResponse 생성
        BusinessExceptionResponse exceptionResponse = new BusinessExceptionResponse(status, message, exception);

        // JSON 응답 작성
        response.setStatus(status.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
