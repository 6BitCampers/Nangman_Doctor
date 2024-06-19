package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.employeeEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper.resetPwMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userResetPwService {
    private final resetPwMapper resetPwMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void resetPw(String uuid,String password) {
        String email = resetPwMapper.findOneByUUID(uuid);
        userEntity user = resetPwMapper.findOneUserByEmail(email);
        if (user == null) {
            employeeEntity employee = resetPwMapper.findOneEmployeeByEmail(email);
            if (employee==null) return;

            //update employee(role,password)
            resetPwMapper.updateEmployeePW(email,passwordEncoder.encode(password));
        }
        else {
            //update employee(role,password)
            resetPwMapper.updateUserPW(email,passwordEncoder.encode(password));
        }
        resetPwMapper.deleteUUID(uuid);
    }

    public boolean checkurl(String uuid) {
        String email = resetPwMapper.findOneByUUID(uuid);
        return email != null;
    }
}
