package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.CustomUserDetails;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.employeeEntity;
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
            employeeEntity employeeuser = userEntityMapper.findEmployeeUserByEmail(email);
            user = new userEntity().builder()
                    .user_name(employeeuser.getEmployee_name())
                    .user_email(employeeuser.getEmployee_email())
                    .user_role(employeeuser.getEmployee_role())
                    .user_type(employeeuser.getEmployee_type())
                    .user_password(employeeuser.getEmployee_pw())
                    .user_gender(employeeuser.getEmployee_gender())
                    .user_addr(employeeuser.getEmployee_addr())
                    .user_hp(employeeuser.getEmployee_hp())
                    .user_age(employeeuser.getEmployee_age())
                    .user_nickname(employeeuser.getEmployee_nickname())
                    .user_interest(employeeuser.getEmployee_interest())
                    .build();
            if(user != null)
                return new CustomUserDetails(user);
            else
                return new CustomUserDetails();
        }
    }
}
