package com.sparta.todo.user.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.user.dto.LoginRequestDto;
import com.sparta.todo.user.jwt.JwtMessage;
import com.sparta.todo.user.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final JwtMessage jwtMessage;


    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtMessage jwtMessage) {
        this.jwtUtil = jwtUtil;
        this.jwtMessage = jwtMessage;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getUsername(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        log.info("로그인 성공 및 JWT 생성");

        String token = jwtUtil.createToken(username);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        String message = "로그인 성공";
        jwtMessage.messageToClient(response, 200, message, "success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException {

        log.info("로그인 실패");
        String message = "로그인 실패";
        jwtMessage.messageToClient(response, 400, message, "error");
    }

}