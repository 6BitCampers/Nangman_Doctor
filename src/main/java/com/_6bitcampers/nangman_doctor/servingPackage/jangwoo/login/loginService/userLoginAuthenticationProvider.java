package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class userLoginAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService userDetailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String useremail = authentication.getName();
        String userpw = (String) authentication.getCredentials();

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailService.loadUserByUsername(useremail);

        if (useremail.equals(customUserDetails.getUsername()) && passwordEncoder.matches(userpw, customUserDetails.getPassword()))
            return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
