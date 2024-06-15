package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import org.springframework.stereotype.Service;

@Service
public class roleService {

    public String checkRole(String role) {
        if (role.equalsIgnoreCase("ROLE_LOGINONLY")) {
            return "loginonly";
        }
        else {
            return "home";
        }
    }
}
