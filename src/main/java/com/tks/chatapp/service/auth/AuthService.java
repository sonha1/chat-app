package com.tks.chatapp.service.auth;

import com.tks.chatapp.common.Const;
import com.tks.chatapp.dto.auth.UserPrincipal;
import com.tks.chatapp.entity.User;
import com.tks.chatapp.enums.UserRole;
import com.tks.chatapp.enums.UserStatus;
import com.tks.chatapp.repository.UserRepository;
import com.tks.chatapp.request.auth.RegisterRequest;
import com.tks.chatapp.util.DataUtils;
import com.tks.chatapp.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOtp = userRepository.findByUsernameAndDeleted(username, false);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (userOtp.isPresent()) {
            User user = userOtp.get();
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setUserId(user.getId());
            Set<GrantedAuthority> authorities = new HashSet<>();
            userPrincipal.setAuthorities(authorities);
        }

        return userPrincipal;
    }

    public Boolean register(RegisterRequest request) {
        validateRequestRequest(request);

        Optional<User> userOtp = userRepository.findByUsername(request.getUsername());
        if (userOtp.isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        userOtp = userRepository.findByEmail(request.getEmail());
        if (userOtp.isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        userOtp = userRepository.findByPhone(request.getPhone());
        if (userOtp.isPresent()) {
            throw new IllegalArgumentException("Phone is already in use");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setStatus(UserStatus.ACTIVE); // TODO: xử lý send email cho việc kích hoặc tài khoản
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return null;
    }

    private void validateRequestRequest(RegisterRequest request) {
        if (request == null
                || DataUtils.isNullOrEmpty(request.getUsername())
                || DataUtils.isNullOrEmpty(request.getPassword())
                || DataUtils.isNullOrEmpty(request.getEmail())
        ) {
            throw new IllegalArgumentException(Const.ERROR_MESSAGE.TOKEN_INVALID);
        }

        Assert.isTrue(ValidateUtil.regexValidation(request.getEmail(), Const.regexEmail), Const.ERROR_MESSAGE.EMAIL_INCORRECT);
        Assert.isTrue(ValidateUtil.regexValidation(request.getPhone(), Const.regexPhone), Const.ERROR_MESSAGE.PHONE_NUMBER_INCORRECT);
    }

}
