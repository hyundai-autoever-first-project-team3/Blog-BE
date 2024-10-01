package hyundai.blog.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    // 생성자에서 SecretKey와 만료 시간을 설정
    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret));  // Base64로 디코딩된 secret 사용
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    // AccessToken 생성
    public String createAccessToken(String userName, String userImg, String userEmail,
            String roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpiration); // AccessToken 만료 시간

        return Jwts.builder()
                .header()   // JWT 토큰 헤더 설정 시작
                .type("JWT")    // typ 필드를 'JWT'로 설정
                .and()  // JWT 토큰 헤더 설정 종료

                .subject("ACCESS TOKEN") // 사용자 이름 설정
                .claim("username", userName) // 사용자 이름
                .claim("userimg", userImg) // 사용자 이미지
                .claim("useremail", userEmail) // 사용자 이메일
                .claim("roles", roles) // 사용자 역할 설정
                .issuedAt(now) // 토큰 발행 시간 설정
                .expiration(validity) // 만료 시간 설정
                .signWith(secretKey)
                .compact();
    }

    // RefreshToken 생성 [Email 값은 중복되지 않으므로 email만 포함되도록 구성]
    public String createRefreshToken(String userEmail) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpiration); // RefreshToken 만료 시간

        return Jwts.builder()
                .subject("REFRESH TOKEN") // 사용자 이름 설정
                .issuedAt(now) // 발행 시간 설정
                .expiration(validity) // 만료 시간 설정
                .signWith(secretKey) // 서명 설정
                .compact();
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return (String) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username");

    }
}
