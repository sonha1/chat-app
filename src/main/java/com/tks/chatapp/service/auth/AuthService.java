package com.tks.chatapp.service.auth;

import com.tks.chatapp.common.Const;
import com.tks.chatapp.dto.auth.UserPrincipal;
import com.tks.chatapp.entity.User;
import com.tks.chatapp.exception.AccountDisableException;
import com.tks.chatapp.exception.UnauthorizedException;
import com.tks.chatapp.repository.UserRepository;
import com.tks.chatapp.request.auth.LoginRequest;
import com.tks.chatapp.response.auth.JwtResponse;
import com.tks.chatapp.util.DataUtils;
import com.tks.chatapp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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



}
