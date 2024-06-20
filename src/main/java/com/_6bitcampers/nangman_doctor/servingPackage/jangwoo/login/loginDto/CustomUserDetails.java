package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {
    private userEntity entity;
    private String role;

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
    public String getPassword() {
        return entity.getUser_password()==null?"":entity.getUser_password();
    }

    @Override
    public String getUsername() {
        return entity.getUser_email();
    }

    public String getEmail() {
        return entity.getUser_email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return entity.getUser_email();
    }

    public String getRealName() {
        return entity.getUser_name();
    }

    public String getRole() {
        return entity.getUser_role();
    }

    public String getNickname() {
        return entity.getUser_nickname();
    }

    public String getType() {
        return entity.getUser_type();
    }

    public int getUserNo(){
        return entity.getUser_no();
    }
}

