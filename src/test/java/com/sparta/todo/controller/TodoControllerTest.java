package com.sparta.todo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.MockSpringSecurityFilter;
import com.sparta.todo.config.WebSecurityConfig;
import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.TodoService;
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
    controllers = {TodoController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class TodoControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

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
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, null,
            testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("일정 생성 test")
    void test1() throws Exception {
        //Given
        this.mockUserSetup();
        String title = "새 제목";
        String content = "새 할일 내용";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title, content);

        // when - then
        mvc.perform(post("/api/todos")
                .content(objectMapper.writeValueAsString(todoRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("선택한 게시글 조회 test")
    void test2() throws Exception {
        //Given
        this.mockUserSetup();
        Long id = 1l;

        //when = then
        mvc.perform(get("/api/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("로그인한 사람이 쓴 글 test")
    void test3() throws Exception {
        //Given
        this.mockUserSetup();

        //when = then
        mvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("전체 게시글 조회 test")
    void test4() throws Exception {
        //Given
        this.mockUserSetup();

        //when = then
        mvc.perform(get("/api/todos/all")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("일정 수정 test")
    void test5() throws Exception {
        //Given
        this.mockUserSetup();
        String title = "수정된 제목";
        String content = "수정된 할일 내용";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title, content);
        Long id = 1l;

        // when - then
        mvc.perform(patch("/api/todos/update/{id}", id)
                .content(objectMapper.writeValueAsString(todoRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("일정 완료 체크 test")
    void test6() throws Exception {
        //Given
        this.mockUserSetup();
        Long id = 1l;

        // when - then
        mvc.perform(patch("/api/todos/{id}/complete", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }


}