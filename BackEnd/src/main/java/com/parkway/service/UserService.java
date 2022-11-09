package com.parkway.service;

import com.parkway.dto.User;
import com.parkway.exception.ResourceNotFoundException;
import com.parkway.model.PasswordChangeRequest;
import com.parkway.model.UserStatus;
import com.parkway.repository.UserRepository;
import com.parkway.security.service.UserDetailsImpl;
import com.parkway.util.Util;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private MailService mailService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(User user, String siteURL) {
        user.setPassword(bCryptPasswordEncoder
                .encode(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setStatus(UserStatus.UNVERIFIED);
        Long userId = userRepository.save(user).getUserId();
        mailService.sendVerificationEmail(user, siteURL);
        return userId;
    }
    @Transactional(rollbackFor = Exception.class)
    public String creatRootUser(String siteURL) {
        if (userRepository.findByEmail("admin@parkaway.com").isPresent()) {
            return "admin user already exists.";
        }
        String password = String.valueOf(UUID.randomUUID());
        User user = new User();
        user.setPassword(password);
        user.setEmail("admin@parkaway.com");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPhone("0000000000");
        user.setStatus(UserStatus.ACTIVE);
        saveUser(user, siteURL);
        return "New Admin User created with Admin privilege. Email : admin@parkaway.com Password :" + password +
                " . Please save the credentials.";
    }

    public User getUserById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new ResourceNotFoundException(userId + " not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        User tempUser = getUserById(user.getUserId());
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder
                    .encode(user.getPassword()));
        }
        BeanUtils.copyProperties(user, tempUser, Util.getNullPropertyNames(user));
        return userRepository.save(tempUser);
    }

    public User getProfile(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return getUserById(userDetails.getUserId());
    }
    @Transactional(rollbackFor = Exception.class)
    public String updatePassword(Principal principal, PasswordChangeRequest passwordChangeRequest) {
        if (!passwordChangeRequest.getNewPassword()
                .equals(passwordChangeRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("New and confirm password should match");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (!bCryptPasswordEncoder.matches(passwordChangeRequest.getCurrentPassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("Current password is wrong");
        }
        User user = new User();
        user.setUserId(userDetails.getUserId());
        user.setPassword(passwordChangeRequest.getNewPassword());
        updateUser(user);
        return "Password updated successfully.";
    }

    public boolean verify(String code) {
        User user = null;
        Optional<User> optionalUser = userRepository.findByVerificationCode(code);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResourceNotFoundException(code + " not found");
        }

        if (user == null) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            return true;
        }
    }
}
