package hyundai.blog.config.security.jwt;

import hyundai.blog.config.jwt.JwtTokenProvider;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret, accessTokenExpiration,
                refreshTokenExpiration);
    }

    @Test
    @DisplayName("액세스 토큰 userName Claim 정상 동작 테스트")
    void test1() {
        // given
        String userName = "zoonmy";
        String userImg = "profile.jpg";
        String userEmail = "zoonmy@naver.com";
        String role = "USER";

        // when
        String actual = jwtTokenProvider.createAccessToken(userName, userImg, userEmail, role);

        // then
        Assertions.assertThrows(InvalidClaimException.class, () -> {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(
                            Decoders.BASE64.decode(secret)))
                    .require("userName", "zoonmy")
                    .build()
                    .parse(actual);
        });


        System.out.println("hi");

    }
}
