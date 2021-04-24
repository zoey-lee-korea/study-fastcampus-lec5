package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class JwtUtilTests {

    private static final String SECRET = "this is super secret key owned by zoey";
    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1L, "zoey", null);
        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2MSwibmFtZSI6InpvZXkifQ.5VV_FcKG8cditWqc5J8d5rshR5-zSoOcfx2tm_qxp5U";
        Claims claims = jwtUtil.getClaims(token);
        assertThat(claims.get("name"),is("zoey"));
    }
}