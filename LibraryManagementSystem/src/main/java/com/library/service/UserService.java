package com.library.service;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email.trim());
    }

    @Transactional
    public User registerStudent(String username, String rawPassword, String fullName, String email, String phone, Integer age) {
        if (usernameExists(username)) {
            throw new IllegalArgumentException("Student ID already exists: " + username);
        }
        User student = new User();
        student.setUsername(username.trim());
        student.setPassword(passwordEncoder.encode(rawPassword));
        student.setFullName(fullName.trim());
        student.setEmail(email != null ? email.trim() : "");
        student.setPhone(phone != null ? phone.trim() : "");
        student.setAge(age != null ? age : 18);
        student.setRole(Role.ROLE_STUDENT);
        return userRepository.save(student);
    }

    @Transactional
    public User registerAdmin(String username, String rawPassword, String fullName, String email) {
        if (usernameExists(username)) {
            throw new IllegalArgumentException("Admin username already exists: " + username);
        }
        User admin = new User();
        admin.setUsername(username.trim());
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setFullName(fullName.trim());
        admin.setEmail(email != null ? email.trim() : "");
        admin.setRole(Role.ROLE_ADMIN);
        return userRepository.save(admin);
    }

    public List<User> getAllStudents() {
        return userRepository.findByRole(Role.ROLE_STUDENT);
    }

    public long countStudents() {
        return userRepository.countByRole(Role.ROLE_STUDENT);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void updateUserProfile(String username, String fullName, String email, String phone, Integer age) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullName(fullName.trim());
        user.setEmail(email != null ? email.trim() : "");
        user.setPhone(phone != null ? phone.trim() : "");
        user.setAge(age != null ? age : user.getAge());
        userRepository.save(user);
    }
}
