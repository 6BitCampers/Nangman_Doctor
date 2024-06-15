package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private userEntity entity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return entity.getUser_role();
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return entity.getUser_password();
    }

    @Override
    public String getUsername() {
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
}

