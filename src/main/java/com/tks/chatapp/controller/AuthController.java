package com.tks.chatapp.controller;

import com.tks.chatapp.common.Const;
import com.tks.chatapp.dto.auth.UserPrincipal;
import com.tks.chatapp.entity.User;
import com.tks.chatapp.exception.AccountDisableException;
import com.tks.chatapp.exception.UnauthorizedException;
import com.tks.chatapp.model.ResponseWrapper;
import com.tks.chatapp.repository.UserRepository;
import com.tks.chatapp.request.auth.LoginRequest;
import com.tks.chatapp.request.auth.RegisterRequest;
import com.tks.chatapp.response.auth.JwtResponse;
import com.tks.chatapp.service.auth.AuthService;
import com.tks.chatapp.util.DataUtils;
import com.tks.chatapp.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.prefix}")
    private String prefixToken;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(
            @RequestBody LoginRequest request
    ) {
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());
        String token = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());
        String rfToken = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());

        return ResponseEntity.ok(
                new ResponseWrapper(
                        new JwtResponse(
                                String.format("%s %s", prefixToken, token),
                                rfToken
                        )
                )
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
        }catch (Exception e) {
            throw new UnauthorizedException(Const.ERROR_MESSAGE.USER_OR_PASS_INCORRECT);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(new ResponseWrapper(
                authService.register(request)
        ));
    }

}

