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
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.prefix}")
    private String prefixToken;

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

    public JwtResponse Login(LoginRequest request) {
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());

        String token = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());
        String rfToken = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());

        return new JwtResponse(
                String.format("%s %s", prefixToken, token),
                rfToken
        );
    }

    private Authentication authenticate(String username, String password) {
        if (DataUtils.isNullOrEmpty(username) || DataUtils.isNullOrEmpty(password)) {
            throw new AccountDisableException(Const.ERROR_MESSAGE.ACCOUNT_DISABLE);
        }

        User user = userRepository.findByUsernameAndDeleted(username, false).orElse(null);
        if (user == null) {
            throw new UnauthorizedException(Const.ERROR_MESSAGE.INVALID_CREDENTIALS);
        }

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new UnauthorizedException(Const.ERROR_MESSAGE.USER_OR_PASS_INCORRECT);
        }

    }
}
