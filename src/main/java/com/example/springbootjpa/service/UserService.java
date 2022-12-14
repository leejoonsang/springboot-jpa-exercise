package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.dto.UserRequest;
import com.example.springbootjpa.domain.dto.UserResponse;
import com.example.springbootjpa.domain.entity.User;
import com.example.springbootjpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUser(Long id) {
        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isEmpty()) {
            return new UserResponse(id, "", "해당 id의 유저가 없습니다.");
        }else{
            User user = optUser.get();
            return new UserResponse(user.getId(), user.getUsername(), "");
        }
    }

    public UserResponse addUser(UserRequest dto) {
        Optional<User> selectedUser = userRepository.findByUsername(dto.getUsername());

        if(selectedUser.isEmpty()) {
            User user = dto.toEntity();
            User savedUser = userRepository.save(user);

            return new UserResponse(savedUser.getId(), savedUser.getUsername(), "회원 등록 완료");
        }else{
            return new UserResponse(null, dto.getUsername(), "이미 존재하는 username 입니다.");
        }




    }
}
