package com.sparta.todo.user.service;


import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.dto.SignupResponseDto;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.jwt.JwtUtil;
import com.sparta.todo.user.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        validUser(username);

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
        return new SignupResponseDto(user);
    }

    public String  login(SignupRequestDto requestDto) {
        String userName = requestDto.getUsername();
        String password = requestDto.getPassword();


        User user = getUserByUsername(userName);

        // 비밀번호 비교
        if (!matchPassword(password, user.getPassword())) {
            // 비밀번호가 일치하지 않으면 예외 처리 또는 다른 처리를 수행
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(user.getUsername());
    }

    private boolean matchPassword(String rawPassword, String encodedPassword) {
        // Spring Security의 PasswordEncoder를 사용하여 비밀번호 비교
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }



    private void validatePassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("패스워드를 잘못 입력하였습니다.");
        }
    }

    private void validUser(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
    }
}