package com.sparta.todo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.config.WebSecurityConfig;
import com.sparta.todo.controller.UserController;
import com.sparta.todo.dto.SignupRequestDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.UserService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {UserController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class UserProductMvcTest {

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "sole451";
        String password = "robbie1234";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, null,testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원가입 Page")
    void test1() throws Exception {
        // given
        String username = "sol435";
        String password = "robbie1234";
        SignupRequestDto requestDto = new SignupRequestDto(username, password);
        userService.signup(requestDto);

        // when - then
        mvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
            )
            .andExpect(status().isOk())
            .andDo(print());

        // UserService의 signup 메소드가 실제로 호출되었는지 확인
        verify(userService).signup(requestDto);


    }
    @Test
    @DisplayName("로그인 Page")
    void test2() throws Exception {
        // given
        mockUserSetup();

        // when - then
        mvc.perform(post("/api/users/login")
                    .principal(mockPrincipal)
                //.with(csrf())  // CSRF 토큰을 함께 전송
//                .with(request -> {
//                    request.setRemoteUser(mockPrincipal.getName());  // 사용자를 인증된 상태로 설정
//                    return request;
//                })
            )
            .andDo(print());
    }
}
