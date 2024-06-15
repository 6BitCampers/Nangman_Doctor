package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class customOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String role;

    //로그인 진행시 넘어오는 데이터
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });

        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getEmail() {
        return oAuth2Response.getEmail();
    }

    public String getType() {return oAuth2Response.getProvider();}
}
