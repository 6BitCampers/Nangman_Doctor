package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper.userEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    final userEntityMapper userEntityMapper;
    final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        userEntity user = userEntityMapper.findNormalUserByEmail(email);

        if (user != null) {
            return new CustomUserDetails(user);
        }
        else {
            user = userEntityMapper.findEmployeeUserByEmail(email);
            if(user != null)
                return new CustomUserDetails(user);
            else
                return new CustomUserDetails();
        }
    }
}
