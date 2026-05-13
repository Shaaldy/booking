package by.shaaldy.booking.service;

import by.shaaldy.booking.dto.request.user.CreateUserRequest;
import by.shaaldy.booking.dto.request.user.UpdateUserRequest;
import by.shaaldy.booking.dto.response.UserResponse;
import by.shaaldy.booking.entity.User;
import by.shaaldy.booking.exception.BusinessException;
import by.shaaldy.booking.exception.ResourceNotFoundException;
import by.shaaldy.booking.mapper.UserMapper;
import by.shaaldy.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(CreateUserRequest request){
        log.info("Registration of user with email: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("User already exists with email: " + request.getEmail());
        }
        User saved = userRepository.save(userMapper.toEntity(request));
        log.info("Registration is successful");
        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) throws ResourceNotFoundException {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) throws ResourceNotFoundException {
        log.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll(){
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException {
        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("User with email '" + request.getEmail() + "' already exists");
        }

        userMapper.updateEntity(request, user);

        User updated = userRepository.save(user);
        log.info("User updated with id: {}", id);

        return userMapper.toResponse(updated);
    }
}
